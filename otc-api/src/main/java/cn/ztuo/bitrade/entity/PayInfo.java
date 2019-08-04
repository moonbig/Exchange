package cn.ztuo.bitrade.entity;

import cn.ztuo.bitrade.entity.Alipay;
import cn.ztuo.bitrade.entity.BankInfo;
import cn.ztuo.bitrade.entity.WechatPay;
import lombok.Builder;
import lombok.Data;

/**
 * @author GS
 * @date 2018年01月20日
 */
@Builder
@Data
public class PayInfo {
    private String realName;
    private Alipay alipay;
    private WechatPay wechatPay;
    private BankInfo bankInfo;
}
