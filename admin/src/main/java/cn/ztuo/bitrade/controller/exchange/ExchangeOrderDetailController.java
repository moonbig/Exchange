package cn.ztuo.bitrade.controller.exchange;

import cn.ztuo.bitrade.constant.PageModel;
import cn.ztuo.bitrade.service.OrderDetailAggregationService;
import cn.ztuo.bitrade.util.MessageResult;

import cn.ztuo.bitrade.controller.common.BaseAdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/exchange/exchange-order-detail")
public class ExchangeOrderDetailController extends BaseAdminController {

    @Autowired
    private OrderDetailAggregationService orderDetailAggregationService;

    @PostMapping("/page-query")
    @ResponseBody
    public MessageResult getOrderDetails(
            PageModel pageModel,
            @RequestParam(value = "memberId", required = false) Long memberId
    ) {
        /*Criteria criteria = new Criteria();
        if(!StringUtils.isEmpty(memberId!=null){
            criteria.where("uidTo").is(message.getUidTo());
        }
        if(!StringUtils.isEmpty(message.getUidFrom())){
            criteria.where("uidFrom").is(message.getUidFrom());
        }*/
        // Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,message.getSortFiled()));
        // Query query = new Query(criteria).with(sort);
        //EntityPage<ExchangeOrderDetailAggregation> result = exchangeOrderDetailAggregationService.findAllByPageNo(criteria,pageNo,pageSize);
        return success();

    }
}
