package cn.ztuo.bitrade.controller;

import com.alibaba.fastjson.JSONObject;

import cn.ztuo.bitrade.Trader.CoinTrader;
import cn.ztuo.bitrade.Trader.CoinTraderFactory;
import cn.ztuo.bitrade.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

	@Autowired
	private CoinTraderFactory factory;

    @RequestMapping("overview")
    public JSONObject  traderOverview(String symbol){
        CoinTrader trader = factory.getTrader(symbol);
        if(trader == null ) {
            return null;
        }
        JSONObject result = new JSONObject();
        //卖盘信息
        JSONObject ask = new JSONObject();
        //买盘信息
        JSONObject bid = new JSONObject();
        ask.put("limit_price_order_count",trader.getLimitPriceOrderCount(ExchangeOrderDirection.SELL));
        ask.put("market_price_order_count",trader.getSellMarketQueue().size());
        ask.put("depth",trader.getTradePlate(ExchangeOrderDirection.SELL).getDepth());
        bid.put("limit_price_order_count",trader.getLimitPriceOrderCount(ExchangeOrderDirection.BUY));
        bid.put("market_price_order_count",trader.getBuyMarketQueue().size());
        bid.put("depth",trader.getTradePlate(ExchangeOrderDirection.BUY).getDepth());
        result.put("ask",ask);
        result.put("bid",bid);
        return result;
    }

    @RequestMapping("trader-detail")
    public JSONObject  traderDetail(String symbol){
        CoinTrader trader = factory.getTrader(symbol);
        if(trader == null ) {
            return null;
        }
        JSONObject result = new JSONObject();
        //卖盘信息
        JSONObject ask = new JSONObject();
        //买盘信息
        JSONObject bid = new JSONObject();
        ask.put("limit_price_queue",trader.getSellLimitPriceQueue());
        ask.put("market_price_queue",trader.getSellMarketQueue());
        bid.put("limit_price_queue",trader.getBuyLimitPriceQueue());
        bid.put("market_price_queue",trader.getBuyMarketQueue());
        result.put("ask",ask);
        result.put("bid",bid);
        return result;
    }

	@RequestMapping("plate")
	public Map<String, List<TradePlateItem>> traderPlate(String symbol) {
		Map<String, List<TradePlateItem>> result = new HashMap<>();
		CoinTrader trader = factory.getTrader(symbol);
		if (trader == null) {
            return null;
        }
		result.put("bid", trader.getTradePlate(ExchangeOrderDirection.BUY).getItems());
		result.put("ask", trader.getTradePlate(ExchangeOrderDirection.SELL).getItems());
		return result;
	}

	@RequestMapping("plate-mini")
	public Map<String, JSONObject> traderPlateMini(String symbol) {
		Map<String, JSONObject> result = new HashMap<>();
		CoinTrader trader = factory.getTrader(symbol);
		if (trader == null) {
            return null;
        }
		result.put("bid", trader.getTradePlate(ExchangeOrderDirection.BUY).toJSON(24));
		result.put("ask", trader.getTradePlate(ExchangeOrderDirection.SELL).toJSON(24));
		return result;
	}

	@RequestMapping("plate-full")
	public Map<String, JSONObject> traderPlateFull(String symbol) {
		Map<String, JSONObject> result = new HashMap<>();
		CoinTrader trader = factory.getTrader(symbol);
		if (trader == null) {
            return null;
        }
		result.put("bid", trader.getTradePlate(ExchangeOrderDirection.BUY).toJSON());
		result.put("ask", trader.getTradePlate(ExchangeOrderDirection.SELL).toJSON());
		return result;
	}

	@RequestMapping("symbols")
	public List<String> symbols() {
		HashMap<String, CoinTrader> traders = factory.getTraderMap();
		List<String> symbols = new ArrayList<>();
		traders.forEach((key, trader) -> {
			symbols.add(key);
		});
		return symbols;
	}

	/**
	 * 查找订单
	 *
	 * @param symbol
	 * @param orderId
	 * @param direction
	 * @param type
	 * @return
	 */
	@RequestMapping("order")
	public ExchangeOrder findOrder(String symbol, String orderId, ExchangeOrderDirection direction,
			ExchangeOrderType type) {
		CoinTrader trader = factory.getTrader(symbol);
		return trader.findOrder(orderId, type, direction);
	}
}
