package cn.ztuo.bitrade.dto;

import lombok.Data;

import java.util.List;

import cn.ztuo.bitrade.entity.Member;
import cn.ztuo.bitrade.entity.MemberWallet;

@Data
public class MemberDTO {

    private Member member ;

    private List<MemberWallet> list ;

}
