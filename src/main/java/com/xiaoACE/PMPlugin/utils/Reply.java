package com.xiaoACE.PMPlugin.utils;

import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.PrivateMessage;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.MarkdownComponent;

/**
 * @author xiaoACE
 */
public class Reply {

    public static String reply(User commandSender, Message message, Object replyMessage, boolean tempFlag){


        String replyMessageID = null;

        //因为私信没有replyTemp，所以先判断是不是私信
        if (message instanceof PrivateMessage){
            //当要回复的内容为文字消息时
            if (replyMessage instanceof String){
                replyMessageID = message.reply(new MarkdownComponent((String) replyMessage));
                //当回复的内容为卡片消息时
            }else if (replyMessage instanceof BaseComponent){
                replyMessageID = message.reply((BaseComponent) replyMessage);
            }
            //然后到频道消息
        }else if (message instanceof TextChannelMessage){
            //when true
            if (tempFlag){
                //当要回复内容为文字消息时
                if (replyMessage instanceof String){
                    replyMessageID = ((TextChannelMessage)message).replyTemp(new MarkdownComponent((String) replyMessage));
                    //当回复内容为卡片消息时
                }else if (replyMessage instanceof BaseComponent){
                    replyMessageID = ((TextChannelMessage)message).replyTemp((BaseComponent) replyMessage);
                }
                //when false
            }else {
                //当要回复内容为文字消息时
                if (replyMessage instanceof String){
                    replyMessageID = message.reply(new MarkdownComponent((String) replyMessage));
                    //当回复内容为卡片消息时
                }else if (replyMessage instanceof BaseComponent){
                    replyMessageID = message.reply((BaseComponent) replyMessage);
                }
            }
        }
        return replyMessageID;
    }

}
