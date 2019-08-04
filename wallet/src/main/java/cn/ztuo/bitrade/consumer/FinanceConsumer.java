package cn.ztuo.bitrade.consumer;

import cn.ztuo.bitrade.constant.WithdrawStatus;
import cn.ztuo.bitrade.entity.WithdrawRecord;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.ztuo.bitrade.constant.BooleanEnum;
import cn.ztuo.bitrade.entity.Coin;
import cn.ztuo.bitrade.service.CoinService;
import cn.ztuo.bitrade.service.MemberWalletService;
import cn.ztuo.bitrade.service.WithdrawRecordService;
import cn.ztuo.bitrade.util.MessageResult;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class FinanceConsumer {
    private Logger logger = LoggerFactory.getLogger(FinanceConsumer.class);
    @Autowired
    private CoinService coinService;
    @Autowired
    private MemberWalletService walletService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WithdrawRecordService withdrawRecordService;

    /**
     * 处理充值消息，key值为币种的名称（注意是全称，如Bitcoin）
     *
     * @param record
     */
    @KafkaListener(topics = {"deposit"})
    public void handleDeposit(ConsumerRecord<String, String> record) {
        logger.info("topic={},key={},value={}", record.topic(), record.key(), record.value());
        if (StringUtils.isEmpty(record.value())) {
            return;
        }
        JSONObject json = JSON.parseObject(record.value());
        if (json == null) {
            return;
        }
        BigDecimal amount = json.getBigDecimal("amount");
        String txid = json.getString("txid");
        String address = json.getString("address");
        Coin coin = coinService.findOne(record.key());
        logger.info("coin={}", coin);

        if (coin != null && walletService.findDeposit(address, txid) == null) {
            MessageResult mr = walletService.recharge(coin, address, amount, txid);
            logger.info("wallet recharge result:{}", mr);
        }
    }

    /**
     * 处理提交请求,调用钱包rpc，自动转账
     *
     * @param record
     */
    @KafkaListener(topics = {"withdraw"})
    public void handleWithdraw(ConsumerRecord<String, String> record) {
        logger.info("topic={},key={},value={}", record.topic(), record.key(), record.value());
        if (StringUtils.isEmpty(record.value())) {
            return;
        }
        JSONObject json = JSON.parseObject(record.value());
        Long withdrawId = json.getLong("withdrawId");
        try {
            String serviceName = "SERVICE-RPC-" + record.key().toUpperCase();
            String url = "http://" + serviceName + "/rpc/withdraw?address={1}&amount={2}&fee={3}";

            Coin coin = coinService.findByUnit(record.key());
            logger.info("coin = {}",coin.toString());
            if (coin != null && coin.getCanAutoWithdraw() == BooleanEnum.IS_TRUE) {
                BigDecimal minerFee = coin.getMinerFee();
                MessageResult result = restTemplate.getForObject(url,
                        MessageResult.class, json.getString("address"), json.getBigDecimal("arriveAmount"), minerFee);
                logger.info("=========================rpc 结束================================");
                logger.info("result = {}", result);
                if (result.getCode() == 0 && result.getData() != null) {
                    logger.info("====================== 处理成功,data为txid更新业务 ==================================");
                    //处理成功,data为txid，更新业务订单
                    String txid = (String) result.getData();
                    withdrawRecordService.withdrawSuccess(withdrawId, txid);
                } else {
                    logger.info("====================== 自动转账失败，转为人工处理 ==================================");
                    //自动转账失败，转为人工处理
                    withdrawRecordService.autoWithdrawFail(withdrawId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("auto withdraw failed,error={}", e.getMessage());
            logger.info("====================== 自动转账失败，转为人工处理 ==================================");
//            自动转账失败，转为人工处理
            withdrawRecordService.autoWithdrawFail(withdrawId);
        }
    }


    /**
     * 异步打钱后返回状态
     * @param record
     */
    @KafkaListener(topics = {"withdraw-notify"})
    public void withdrawNotify(ConsumerRecord<String, String> record){
        logger.info("topic={},accessKey={},value={}", record.topic(), record.key(), record.value());
        if (StringUtils.isEmpty(record.value())) {
            return;
        }
        JSONObject json = JSON.parseObject(record.value());
        Long withdrawId = json.getLong("withdrawId");
        WithdrawRecord withdrawRecord=withdrawRecordService.findOne(withdrawId);
        if(withdrawRecord==null){
            return;
        }
        String txid=json.getString("txid");
        int status=json.getInteger("status");
        //转账失败，状态变回等待放币
        if(status==0){
            withdrawRecord.setStatus(WithdrawStatus.WAITING);
            withdrawRecordService.save(withdrawRecord);
        }else if(status==1){
            withdrawRecordService.withdrawSuccess(withdrawId, txid);
        }
    }
}
