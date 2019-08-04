package cn.ztuo.bitrade.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.ztuo.bitrade.entity.MemberLog;


public interface MemberLogDao extends MongoRepository<MemberLog,Long> {
}
