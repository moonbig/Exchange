package cn.ztuo.bitrade.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.Auto;

import cn.ztuo.bitrade.constant.Platform;
import cn.ztuo.bitrade.constant.SysAdvertiseLocation;
import cn.ztuo.bitrade.constant.SysConstant;
import cn.ztuo.bitrade.constant.SysHelpClassification;
import cn.ztuo.bitrade.controller.BaseController;
import cn.ztuo.bitrade.entity.AppRevision;
import cn.ztuo.bitrade.entity.SysAdvertise;
import cn.ztuo.bitrade.entity.SysHelp;
import cn.ztuo.bitrade.entity.WebsiteInformation;
import cn.ztuo.bitrade.service.AppRevisionService;
import cn.ztuo.bitrade.service.SysAdvertiseService;
import cn.ztuo.bitrade.service.SysHelpService;
import cn.ztuo.bitrade.service.WebsiteInformationService;
import cn.ztuo.bitrade.util.MessageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author GS
 * @date 2018年02月05日
 */
@RestController
@RequestMapping("/ancillary")
@Slf4j
public class AideController extends BaseController{
    @Autowired
    private WebsiteInformationService websiteInformationService;

    @Autowired
    private SysAdvertiseService sysAdvertiseService;

    @Autowired
    private SysHelpService sysHelpService;
    @Autowired
    private AppRevisionService appRevisionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 站点信息
     *
     * @return
     */
    @RequestMapping("/website/info")
    public MessageResult keyWords() {
        WebsiteInformation websiteInformation = websiteInformationService.fetchOne();
        MessageResult result = MessageResult.success();
        result.setData(websiteInformation);
        return result;
    }

    /**
     * 系统广告
     *
     * @return
     */
    @RequestMapping("/system/advertise")
    public MessageResult sysAdvertise(@RequestParam(value = "sysAdvertiseLocation", required = false) SysAdvertiseLocation sysAdvertiseLocation) {
        List<SysAdvertise> list = sysAdvertiseService.findAllNormal(sysAdvertiseLocation);
        MessageResult result = MessageResult.success();
        result.setData(list);
        return result;
    }


    /**
     * 系统帮助
     *
     * @return
     */
    @RequestMapping("/system/help")
    public MessageResult sysHelp(@RequestParam(value = "sysHelpClassification", required = false) SysHelpClassification sysHelpClassification) {
        List<SysHelp> list = null;
        if (sysHelpClassification == null) {
            list = sysHelpService.findAll();
        } else {
            list = sysHelpService.findBySysHelpClassification(sysHelpClassification);
        }
        MessageResult result = MessageResult.success();
        result.setData(list);
        return result;
    }

    /**
     * 查询帮助中心首页数据
     * @param total
     * @return
     */
    @RequestMapping(value = "more/help",method = RequestMethod.POST)
    public MessageResult sysAllHelp(@RequestParam(value = "total",defaultValue = "6")int total){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<JSONObject> result = (List<JSONObject>) valueOperations.get(SysConstant.SYS_HELP);
        if (result != null){
            return success(result);
        } else {
            //HELP("新手指南"),
            List<JSONObject> jsonResult = new ArrayList<>();
            Page<SysHelp> sysHelpPage = sysHelpService.findByCondition(1,total,SysHelpClassification.HELP);
            JSONObject jsonSysHelp = new JSONObject();
            jsonSysHelp.put("content",sysHelpPage.getContent());
            jsonSysHelp.put("title","新手指南");
            jsonSysHelp.put("cate","0");
            jsonResult.add(jsonSysHelp);

            //FAQ("常见问题"),
            Page<SysHelp> sysFaqPage = sysHelpService.findByCondition(1,total,SysHelpClassification.FAQ);
            JSONObject jsonSysFaq = new JSONObject();
            jsonSysFaq.put("content",sysFaqPage.getContent());
            jsonSysFaq.put("title","常见问题");
            jsonSysFaq.put("cate","1");
            jsonResult.add(jsonSysFaq);
            valueOperations.set(SysConstant.SYS_HELP,jsonResult,SysConstant.SYS_HELP_EXPIRE_TIME, TimeUnit.SECONDS);
            return success(jsonResult);
        }


        //RECHARGE("充值指南"),
        //Page<SysHelp> sysRechangePage = sysHelpService.findByCondition(1,total,SysHelpClassification.HELP);

        //TRANSACTION("交易指南"),
        //Page<SysHelp> sysTransactonPage = sysHelpService.findByCondition(1,total,SysHelpClassification.HELP);




    }

    /**
     * 获取该分类（二级页面）
     * @param pageNo
     * @param pageSize
     * @param cate
     * @return
     */
    @RequestMapping(value = "more/help/page",method = RequestMethod.POST)
    public MessageResult sysHelpCate(@RequestParam(value = "pageNo",defaultValue = "1")int pageNo,
                                    @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                    @RequestParam(value = "cate")SysHelpClassification cate){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        JSONObject result = (JSONObject) valueOperations.get(SysConstant.SYS_HELP_CATE+cate);
        if (result != null){
            return success(result);
        }else {
            JSONObject jsonObject = new JSONObject();
            Page<SysHelp> sysHelpPage = sysHelpService.findByCondition(pageNo,pageSize,cate);
            jsonObject.put("content",sysHelpPage.getContent());
            jsonObject.put("totalPage",sysHelpPage.getTotalPages());
            jsonObject.put("totalElements",sysHelpPage.getTotalElements());
            valueOperations.set(SysConstant.SYS_HELP_CATE+cate,jsonObject,SysConstant.SYS_HELP_CATE_EXPIRE_TIME, TimeUnit.SECONDS);
            return success(jsonObject);

        }

    }

    /**
     * 获取该分类的置顶文章
     * @param cate
     * @return
     */
    @RequestMapping(value = "more/help/page/top", method = RequestMethod.POST)
    public MessageResult sysHelpTop(@RequestParam(value = "cate")String cate){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<SysHelp> result = (List<SysHelp>) valueOperations.get(SysConstant.SYS_HELP_TOP+cate);
        if ( result != null && !result.isEmpty()){
            return success(result);
        }else {
            List<SysHelp> sysHelps = sysHelpService.getgetCateTops(cate);
            valueOperations.set(SysConstant.SYS_HELP_TOP+cate,sysHelps,SysConstant.SYS_HELP_TOP_EXPIRE_TIME,TimeUnit.SECONDS);
            return success(sysHelps);
        }
    }


    /**
     * 系统帮助详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "more/help/detail",method = RequestMethod.POST)
    public MessageResult sysHelpDetail(@RequestParam(value = "id") long id) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        SysHelp result = (SysHelp) valueOperations.get(SysConstant.SYS_HELP_DETAIL+id);
        if (result != null){
            return success(result);
        }else {
            SysHelp sysHelp = sysHelpService.findOne(id);
            valueOperations.set(SysConstant.SYS_HELP_DETAIL+id,sysHelp,SysConstant.SYS_HELP_DETAIL_EXPIRE_TIME,TimeUnit.SECONDS);
            return success(sysHelp);
        }

    }






    /**
     * 系统帮助详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/system/help/{id}")
    public MessageResult sysHelp(@PathVariable(value = "id") long id) {
        //List<SysHelp> list = sysHelpService.findBySysHelpClassification(sysHelpClassification);
        SysHelp sysHelp = sysHelpService.findOne(id);
        MessageResult result = MessageResult.success();
        result.setData(sysHelp);
        return result;
    }

    /**
     * 移动版本号
     *
     * @param platform 0:安卓 1:苹果
     * @return
     */
    @RequestMapping("/system/app/version/{id}")
    public MessageResult sysHelp(@PathVariable(value = "id") Platform platform) {

        AppRevision revision = appRevisionService.findRecentVersion(platform);
        if(revision != null){
            MessageResult result = MessageResult.success();
            result.setData(revision);
            return result;
        }
        else{
            return MessageResult.error("no update");
        }
    }

}
