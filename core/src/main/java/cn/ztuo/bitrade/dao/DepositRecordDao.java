package cn.ztuo.bitrade.dao;

import java.util.List;

import cn.ztuo.bitrade.constant.DepositStatusEnum;
import cn.ztuo.bitrade.dao.base.BaseDao;
import cn.ztuo.bitrade.entity.DepositRecord;
import cn.ztuo.bitrade.entity.Member;

/**
 * @author zhang yingxin
 * @date 2018/5/7
 */
public interface DepositRecordDao extends BaseDao<DepositRecord> {
    public DepositRecord findById(String id);

    public List<DepositRecord> findByMemberAndStatus(Member member, DepositStatusEnum status);
}
