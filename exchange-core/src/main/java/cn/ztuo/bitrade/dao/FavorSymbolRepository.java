package cn.ztuo.bitrade.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.ztuo.bitrade.entity.FavorSymbol;

import java.util.List;

public interface FavorSymbolRepository extends JpaRepository<FavorSymbol,Long>{
    FavorSymbol findByMemberIdAndSymbol(Long memberId,String symbol);
    List<FavorSymbol> findAllByMemberId(Long memberId);
}
