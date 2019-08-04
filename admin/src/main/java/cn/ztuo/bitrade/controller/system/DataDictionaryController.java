package cn.ztuo.bitrade.controller.system;

import cn.ztuo.bitrade.constant.PageModel;
import cn.ztuo.bitrade.entity.DataDictionary;
import cn.ztuo.bitrade.service.DataDictionaryService;
import cn.ztuo.bitrade.util.BindingResultUtil;
import cn.ztuo.bitrade.util.MessageResult;

import cn.ztuo.bitrade.controller.common.BaseAdminController;
import cn.ztuo.bitrade.model.create.DataDictionaryCreate;
import cn.ztuo.bitrade.model.update.DataDictionaryUpdate;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author GS
 * @Title: ${file_name}
 * @Description:
 * @date 2018/4/1214:21
 */
@Slf4j
@RestController
@RequestMapping("system/data-dictionary")
public class DataDictionaryController extends BaseAdminController {
    @Autowired
    private DataDictionaryService service;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public MessageResult post(@Valid DataDictionaryCreate model, BindingResult bindingResult) {
        log.info(">>>>>>新增键值对>>>key>>"+model.getBond()+">>>>value>>"+model.getValue());
        MessageResult result = BindingResultUtil.validate(bindingResult);
        if (result != null) {
            return result;
        }
        DataDictionary data = service.findByBond(model.getBond());
        if (data != null) {
            return error("bond already existed!");
        }
        service.save(model);
        kafkaTemplate.send("data-dictionary-save-update",model.getBond(),model.getValue());
        log.info(">>>>>新增结束>>>>");
        return success();
    }

    @GetMapping
    public MessageResult page(PageModel pageModel) {
        Page<DataDictionary> all = service.findAll(null, pageModel);
        return success(all);
    }
    @RequiresPermissions("system:data-dictionary:update")
    @PutMapping("/{bond}")
    public MessageResult put(@PathVariable("bond") String bond, DataDictionaryUpdate model) {
        log.info(">>>>>修改键值对>>>key>>"+bond+">>>value>>"+model.getValue());
        DataDictionary dataDictionary = service.findByBond(bond);
        Assert.notNull(dataDictionary, "validate bond");
        service.update(model, dataDictionary);
        dataDictionary.setValue(model.getValue());
        dataDictionary.setComment(model.getComment());
        kafkaTemplate.send("data-dictionary-save-update",bond,dataDictionary.getValue());
        return success();
    }


}
