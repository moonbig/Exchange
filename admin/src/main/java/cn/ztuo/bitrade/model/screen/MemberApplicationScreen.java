package cn.ztuo.bitrade.model.screen;

import cn.ztuo.bitrade.constant.AuditStatus;
import lombok.Data;

@Data
public class MemberApplicationScreen extends AccountScreen{
    private AuditStatus auditStatus;//审核状态
    private String cardNo ; //身份证号
}
