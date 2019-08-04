package cn.ztuo.bitrade.handler;

import cn.ztuo.bitrade.entity.ChatMessageRecord;
import cn.ztuo.bitrade.entity.HistoryChatMessage;
import cn.ztuo.bitrade.entity.HistoryMessagePage;

public interface MessageHandler {

    void handleMessage(ChatMessageRecord message);

    HistoryMessagePage getHistoryMessage(HistoryChatMessage message);
}
