package cn.ztuo.bitrade.dao;

import org.springframework.data.jpa.repository.Query;

import cn.ztuo.bitrade.dao.base.BaseDao;
import cn.ztuo.bitrade.dto.SmsDTO;

import java.util.List;

/**
 * @Description:
 * @author: GuoShuai
 * @date: create in 9:47 2018/6/28
 * @Modified:
 */
public interface SmsDao extends BaseDao<SmsDTO> {
    
    @Query(value = "select * from tb_sms where sms_status = '0' ",nativeQuery = true)
    SmsDTO findBySmsStatus();
}
