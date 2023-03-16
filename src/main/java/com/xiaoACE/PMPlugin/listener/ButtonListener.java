package com.xiaoACE.PMPlugin.listener;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiaoACE.PMPlugin.Main;
import snw.jkook.JKook;
import snw.jkook.entity.Guild;
import snw.jkook.entity.Role;
import snw.jkook.entity.User;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.user.UserClickButtonEvent;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.util.PageIterator;

import java.util.Set;

import static com.xiaoACE.PMPlugin.utils.allTheCard.buildCardTwo;

public class ButtonListener implements Listener {

    @EventHandler
    public void ButtonEvent(UserClickButtonEvent event){

        String eventRawJson = event.getValue();

        JSONObject eventJson = null;
        try{
            eventJson = JSONUtil.parseObj(eventRawJson,false);
        }catch (JSONException e){
            e.printStackTrace();
            System.out.println("序列化JSON时出现异常,将打印异常raw");
            System.out.println(eventRawJson);
        }

        String actionName = eventJson.getStr("action-name");
        String user_full_name = eventJson.getStr("user_full_name","傻逼！");
        String want_role = eventJson.getStr("want_role","未提供想要的权限");
        String user_game_name = eventJson.getStr("user_game_name","未提供游戏ID");
        String qq_number = eventJson.getStr("qq_number","未提供QQ号");
        String user_id = eventJson.getStr("user_id");

        if (actionName.equals("通过")){

            System.out.println("通过"+user_full_name+"的权限申请");

            Guild guild = getGuild();

            User user = JKook.getHttpAPI().getUser(user_id);

            //初始化 PageIterator<Set<Role>> roles
            PageIterator<Set<Role>> roles = null;
            if(guild != null){
                roles = guild.getRoles();
            }
            while (roles.hasNext()){
                Set<Role> roleSet = roles.next();
                for (Role role:roleSet){
                    if (role.getName().equals(want_role)){
                        user.grantRole(role);
                    }
                }
            }


            TextChannelMessage message = JKook.getCore().getUnsafe().getTextChannelMessage(event.getMessageId());

            MultipleCardComponent multipleCardComponent = buildCardTwo(eventJson);

            message.setComponent(multipleCardComponent);




        }else if(actionName.equals("不通过")) {

            System.out.println("不通过"+user_full_name+"的权限申请");

            User user = JKook.getHttpAPI().getUser(user_id);

            String messageString = "您的申请: {} 权限请求不通过";
            messageString = StrUtil.format(messageString,want_role);

            user.sendPrivateMessage(messageString);

            TextChannelMessage message = JKook.getCore().getUnsafe().getTextChannelMessage(event.getMessageId());

            MultipleCardComponent multipleCardComponent = buildCardTwo(eventJson);

            message.setComponent(multipleCardComponent);

        }


    }

    private static Guild getGuild(){

        String Guild_ID = Main.getInstance().getConfig().getString("Guild_ID");
        Guild guild = JKook.getHttpAPI().getGuild(Guild_ID);

        return guild;
    }

}
