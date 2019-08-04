package cn.ztuo.bitrade.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.ztuo.bitrade.entity.ExchangeOrderDetail;
import cn.ztuo.bitrade.entity.ExchangeTrade;

public interface ExchangeTradeRepository extends MongoRepository<ExchangeTrade,String> {
}
