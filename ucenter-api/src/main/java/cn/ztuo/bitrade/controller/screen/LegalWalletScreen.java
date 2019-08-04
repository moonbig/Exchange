package cn.ztuo.bitrade.controller.screen;

import cn.ztuo.bitrade.constant.LegalWalletState;
import lombok.Data;

/**
 * @author GS
 * @Title: ${file_name}
 * @Description:
 * @date 2018/4/217:44
 */
@Data
public class LegalWalletScreen {
    private LegalWalletState state;
    private String coinName;
}
