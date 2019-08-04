package cn.ztuo.bitrade.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.ztuo.bitrade.constant.PromotionLevel;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author GS
 * @date 2018年03月20日
 */
@Data
@Builder
public class PromotionMember {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String username;
    private PromotionLevel level;
}
