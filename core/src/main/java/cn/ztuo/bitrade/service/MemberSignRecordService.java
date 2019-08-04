package cn.ztuo.bitrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ztuo.bitrade.dao.MemberSignRecordDao;
import cn.ztuo.bitrade.entity.MemberSignRecord;
import cn.ztuo.bitrade.service.Base.TopBaseService;

/**
 * @author GS
 * @Description:
 * @date 2018/5/410:19
 */
@Service
public class MemberSignRecordService extends TopBaseService<MemberSignRecord, MemberSignRecordDao> {
    @Override
    @Autowired
    public void setDao(MemberSignRecordDao dao) {
        super.setDao(dao);
    }
}
