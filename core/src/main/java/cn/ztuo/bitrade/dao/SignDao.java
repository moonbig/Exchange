package cn.ztuo.bitrade.dao;

import cn.ztuo.bitrade.constant.SignStatus;
import cn.ztuo.bitrade.dao.base.BaseDao;
import cn.ztuo.bitrade.entity.Sign;

/**
 * @author GS
 * @Description:
 * @date 2018/5/311:10
 */
public interface SignDao extends BaseDao<Sign> {
    Sign findByStatus(SignStatus status);
}
