package cn.ztuo.bitrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ztuo.bitrade.dao.HotTransferRecordDao;
import cn.ztuo.bitrade.entity.HotTransferRecord;
import cn.ztuo.bitrade.service.Base.TopBaseService;

@Service
public class HotTransferRecordService extends TopBaseService<HotTransferRecord,HotTransferRecordDao> {

    @Override
    @Autowired
    public void setDao(HotTransferRecordDao dao) {
        super.setDao(dao);
    }
}
