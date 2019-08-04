package cn.ztuo.bitrade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ztuo.bitrade.entity.HistoryChatMessage;
import cn.ztuo.bitrade.entity.HistoryMessagePage;
import cn.ztuo.bitrade.handler.MessageHandler;

@RestController
public class HistoryMessageController {

    @Autowired
    private MessageHandler chatMessageHandler ;

    @RequestMapping("/getHistoryMessage")
    public HistoryMessagePage getHistoryMessage(HistoryChatMessage message){
        return chatMessageHandler.getHistoryMessage(message);
    }
}
