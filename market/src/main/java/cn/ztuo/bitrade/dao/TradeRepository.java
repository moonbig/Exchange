package cn.ztuo.bitrade.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.ztuo.bitrade.entity.ExchangeTrade;

public interface TradeRepository extends MongoRepository<ExchangeTrade,Long>{
}
