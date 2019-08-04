package cn.ztuo.bitrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.ztuo.bitrade.MarketApplication;
import cn.ztuo.bitrade.dao.OrderDetailAggregationRepository;
import cn.ztuo.bitrade.entity.OrderDetailAggregation;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author GS
 * @date 2018年03月22日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=MarketApplication.class)
public class FeeTest {
    @Autowired
    private OrderDetailAggregationRepository orderDetailAggregationRepository;
    @Test
    public void contextLoads() throws Exception {
        OrderDetailAggregation o=new OrderDetailAggregation();
        o.setAmount(new BigDecimal("100.92349876").doubleValue());
        o.setFee(new BigDecimal("100.188888876").doubleValue());
        o.setTime(Calendar.getInstance().getTimeInMillis());
        o.setUnit("USDT");
        orderDetailAggregationRepository.save(o);
        OrderDetailAggregation d=new OrderDetailAggregation();
        d.setAmount(new BigDecimal("100.92349876").doubleValue());
        d.setFee(new BigDecimal("100.8").doubleValue());
        d.setTime(Calendar.getInstance().getTimeInMillis());
        d.setUnit("USDT");
        orderDetailAggregationRepository.save(d);
        OrderDetailAggregation b=new OrderDetailAggregation();
        b.setAmount(new BigDecimal("100.92349876").doubleValue());
        b.setFee(new BigDecimal("100.188888876").doubleValue());
        b.setTime(Calendar.getInstance().getTimeInMillis());
        b.setUnit("BTC");
        orderDetailAggregationRepository.save(b);
    }
}
