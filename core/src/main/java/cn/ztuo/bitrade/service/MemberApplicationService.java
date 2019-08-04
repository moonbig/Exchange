package cn.ztuo.bitrade.service;

import com.alibaba.fastjson.JSONObject;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import cn.ztuo.bitrade.constant.*;
import cn.ztuo.bitrade.dao.MemberApplicationDao;
import cn.ztuo.bitrade.dao.MemberDao;
import cn.ztuo.bitrade.entity.*;
import cn.ztuo.bitrade.es.ESUtils;
import cn.ztuo.bitrade.pagination.PageResult;
import cn.ztuo.bitrade.service.Base.BaseService;
import cn.ztuo.bitrade.util.BigDecimalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.ztuo.bitrade.constant.AuditStatus.AUDIT_DEFEATED;
import static cn.ztuo.bitrade.constant.AuditStatus.AUDIT_SUCCESS;
import static cn.ztuo.bitrade.constant.RealNameStatus.NOT_CERTIFIED;
import static cn.ztuo.bitrade.constant.RealNameStatus.VERIFIED;
import static cn.ztuo.bitrade.entity.QMemberApplication.memberApplication;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author GS
 * @description 会员审核单Service
 * @date 2017/12/26 15:10
 */
@Service
@Slf4j
public class MemberApplicationService extends BaseService {

    @Autowired
    private MemberApplicationDao memberApplicationDao;

    @Value("${commission.need.real-name:0}")
    private int needRealName ;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberWalletService memberWalletService ;

    @Autowired
    private RewardRecordService rewardRecordService ;

    @Autowired
    private RewardPromotionSettingService rewardPromotionSettingService ;

    @Autowired
    private MemberTransactionService memberTransactionService ;

    @Autowired
    private MemberPromotionService memberPromotionService ;

    @Autowired
    private ESUtils esUtils;


    @Override
    public List<MemberApplication> findAll() {
        return memberApplicationDao.findAll();
    }

    public Page<MemberApplication> findAll(Predicate predicate, Pageable pageable) {
        return memberApplicationDao.findAll(predicate, pageable);
    }

    public MemberApplication findOne(Long id) {
        return memberApplicationDao.findOne(id);
    }

    public MemberApplication save(MemberApplication memberApplication) {
        return memberApplicationDao.save(memberApplication);
    }

    public List<MemberApplication> findLatelyReject(Member member) {
        return memberApplicationDao.findMemberApplicationByMemberAndAuditStatusOrderByIdDesc(member, AuditStatus.AUDIT_DEFEATED);
    }

    /**
     * 条件查询对象 pageNo pageSize 同时传时分页
     *
     * @param predicateList
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true)
    public PageResult<MemberApplication> query(List<Predicate> predicateList, Integer pageNo, Integer pageSize) {
        List<MemberApplication> list;
        JPAQuery<MemberApplication> jpaQuery = queryFactory.selectFrom(memberApplication);
        if (predicateList != null) {
            jpaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        }
        jpaQuery.orderBy(memberApplication.createTime.desc());
        if (pageNo != null && pageSize != null) {
            list = jpaQuery.offset((pageNo - 1) * pageSize).limit(pageSize).fetch();
        } else {
            list = jpaQuery.fetch();
        }
        return new PageResult<>(list, jpaQuery.fetchCount());
    }

    /**
     * 审核通过
     *
     * @param application
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditPass(MemberApplication application) {
        Member member = application.getMember();
        member.setMemberLevel(MemberLevelEnum.REALNAME);//实名会员
        member.setRealName(application.getRealName());//添加会员真实姓名
        member.setIdNumber(application.getIdCard());//会员身份证号码
        member.setRealNameStatus(VERIFIED);//会员状态修改已认证
        member.setApplicationTime(new Date());
        memberDao.save(member);
        application.setAuditStatus(AUDIT_SUCCESS);//审核成功
        //审核通过给奖励
//       if(needRealName==1){
//            if(member.getInviterId()!=null) {
//                Member member1 = memberDao.findOne(member.getInviterId());
//                promotion(member1, member);
//                bhbChangeService.saveBhbChange(BigDecimal.valueOf(60),member.getInviterId(),"2","实名后送推广人");
//            }
//        }
        memberApplicationDao.save(application);
//        //初始化用户财富信息表
//        WealthInfo wealthInfo = new WealthInfo();
//        long memberId = member.getId();
//        wealthInfo.setMemberId(memberId);
//        wealthInfo.setGiveBhb(BigDecimal.valueOf(10000));
//        wealthInfo.setMemberRate("0");
//        wealthInfo.setReleaseRate("1");
//        wealthInfo.setPromotionTime(DateUtil.dateToString(new Date()));
//        wealthInfo.setAccumulatedMine(BigDecimal.valueOf(0));
//        wealthInfo.setMemberMobile(member.getMobilePhone());
//        wealthInfo.setMemberName(member.getRealName());
//        wealthInfo.setInviterId(member.getInviterId());
//        wealthInfoService.save(wealthInfo);
//        //添加用户BHBchange记录
//        bhbChangeService.saveBhbChange(BigDecimal.valueOf(10000),memberId,"1","新用户赠送");




    }

    private void promotion(Member member1, Member member) {
        RewardPromotionSetting rewardPromotionSetting = rewardPromotionSettingService.findByType(PromotionRewardType.REGISTER);
        if (rewardPromotionSetting != null) {
            MemberWallet memberWallet1 = memberWalletService.findByCoinAndMember(rewardPromotionSetting.getCoin(), member1);
            BigDecimal amount1 = JSONObject.parseObject(rewardPromotionSetting.getInfo()).getBigDecimal("one");
            memberWallet1.setBalance(BigDecimalUtils.add(memberWallet1.getBalance(), amount1));
            memberWalletService.save(memberWallet1);
            RewardRecord rewardRecord1 = new RewardRecord();
            rewardRecord1.setAmount(amount1);
            rewardRecord1.setCoin(rewardPromotionSetting.getCoin());
            rewardRecord1.setMember(member1);
            rewardRecord1.setRemark(rewardPromotionSetting.getType().getCnName());
            rewardRecord1.setType(RewardRecordType.PROMOTION);
            rewardRecordService.save(rewardRecord1);
            MemberTransaction memberTransaction = new MemberTransaction();
            memberTransaction.setFee(BigDecimal.ZERO);
            memberTransaction.setAmount(amount1);
            memberTransaction.setSymbol(rewardPromotionSetting.getCoin().getUnit());
            memberTransaction.setType(TransactionType.PROMOTION_AWARD);
            memberTransaction.setMemberId(member1.getId());
            memberTransaction.setRealFee("0");
            memberTransaction.setDiscountFee("0");
            memberTransaction.setCreateTime(new Date());
            memberTransactionService.save(memberTransaction);

        }
        member1.setFirstLevel(member1.getFirstLevel() + 1);
        //member.setInviterId(member1.getId());
        MemberPromotion one = new MemberPromotion();
        one.setInviterId(member1.getId());
        one.setInviteesId(member.getId());
        one.setLevel(PromotionLevel.ONE);
        memberPromotionService.save(one);
        //取消二级赠送
//        if (member1.getInviterId() != null) {
//            Member member2 = memberDao.findOne(member1.getInviterId());
//            if (needRealName == 1) {
//                promotionLevelTwo(rewardPromotionSetting, member2, member);
//            }
//        }
    }

    private void promotionLevelTwo(RewardPromotionSetting rewardPromotionSetting, Member member2, Member member) {
        if (rewardPromotionSetting != null) {
            MemberWallet memberWallet2 = memberWalletService.findByCoinAndMember(rewardPromotionSetting.getCoin(), member2);
            BigDecimal amount2 = JSONObject.parseObject(rewardPromotionSetting.getInfo()).getBigDecimal("two");
            memberWallet2.setBalance(BigDecimalUtils.add(memberWallet2.getBalance(), amount2));
            memberWalletService.save(memberWallet2);
            RewardRecord rewardRecord2 = new RewardRecord();
            rewardRecord2.setAmount(amount2);
            rewardRecord2.setCoin(rewardPromotionSetting.getCoin());
            rewardRecord2.setMember(member2);
            rewardRecord2.setRemark(rewardPromotionSetting.getType().getCnName());
            rewardRecord2.setType(RewardRecordType.PROMOTION);
            rewardRecordService.save(rewardRecord2);
            MemberTransaction memberTransaction = new MemberTransaction();
            memberTransaction.setFee(BigDecimal.ZERO);
            memberTransaction.setAmount(amount2);
            memberTransaction.setSymbol(rewardPromotionSetting.getCoin().getUnit());
            memberTransaction.setType(TransactionType.PROMOTION_AWARD);
            memberTransaction.setMemberId(member2.getId());
            memberTransaction.setRealFee("0");
            memberTransaction.setDiscountFee("0");
            memberTransaction.setCreateTime(new Date());
            memberTransactionService.save(memberTransaction);
        }
        member2.setSecondLevel(member2.getSecondLevel() + 1);
        MemberPromotion two = new MemberPromotion();
        two.setInviterId(member2.getId());
        two.setInviteesId(member.getId());
        two.setLevel(PromotionLevel.TWO);
        memberPromotionService.save(two);
        if (member2.getInviterId() != null) {
            Member member3 = memberDao.findOne(member2.getInviterId());
            member3.setThirdLevel(member3.getThirdLevel() + 1);
        }
    }


    public long countAuditing(){
        return memberApplicationDao.countAllByAuditStatus(AuditStatus.AUDIT_ING);
    }




    /**
     * 审核不通过
     *
     * @param application
     */
    @Transactional
    public void auditNotPass(MemberApplication application) {
        Member member = application.getMember();
        member.setRealNameStatus(NOT_CERTIFIED);//会员实名状态未认证
        member.setMemberLevel(MemberLevelEnum.GENERAL);//实名会员
        member.setRealName(null);//重置会员名字
        member.setIdNumber(null);//重置会员身份证号
        member.setApplicationTime(null);//重置会员实名时间
        memberDao.save(member);
        application.setAuditStatus(AUDIT_DEFEATED);//审核失败
        memberApplicationDao.save(application);
    }

    /**
     * 根据身份证号 查询有多条记录
     * @param idCard
     * @return
     */
    public int queryByIdCard(String idCard) {
        return memberApplicationDao.queryByIdCard(idCard);
    }
}
