package cn.ztuo.bitrade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ztuo.bitrade.dao.FeedbackDao;
import cn.ztuo.bitrade.entity.Feedback;
import cn.ztuo.bitrade.service.Base.BaseService;

/**
 * @author GS
 * @date 2018年03月19日
 */
@Service
public class FeedbackService extends BaseService {
    @Autowired
    private FeedbackDao feedbackDao;

    public Feedback save(Feedback feedback){
        return feedbackDao.save(feedback);
    }
}
