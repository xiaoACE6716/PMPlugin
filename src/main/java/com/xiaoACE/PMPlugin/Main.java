package com.xiaoACE.PMPlugin;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.xiaoACE.PMPlugin.listener.ButtonListener;
import snw.jkook.JKook;
import snw.jkook.command.JKookCommand;
import snw.jkook.entity.Guild;
import snw.jkook.entity.Role;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.jkook.event.Listener;
import snw.jkook.message.PrivateMessage;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.plugin.BasePlugin;
import snw.jkook.util.PageIterator;

import java.util.Set;

import static com.xiaoACE.PMPlugin.utils.allTheCard.buildCardOne;

/**
 * @author xiaoACE
 */
public class Main extends BasePlugin {

    private static Main INSTANCE;

    public static Main getInstance() {
        return INSTANCE;
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable(){
        INSTANCE = this;
        addListener(new ButtonListener());

        new JKookCommand("申请权限","/")
                //.addPrefix(".").addPrefix("#")
                .setDescription("用法: /申请权限 [想要申请的权限] [QQ号] [游戏ID] ")
                .setHelpContent("用法: /申请权限 [想要申请的权限] [QQ号] [游戏ID] ")
                .addArgument(String.class)
                .addOptionalArgument(String.class,"未提供QQ号")
                .addOptionalArgument(String.class,"未提供游戏ID")
                .executesUser((sender, arguments, message) -> {

                    //初始化Guild
                    Guild guild = null;
                    //因为无论是在私信里使用命令还是在频道里使用命令，都要拿到Guild对象，所以为了避免私信无法拿到Guild，用Guild_ID来拿Guild对象
                    if (message instanceof PrivateMessage){
                        String Guild_ID = getConfig().getString("Guild_ID");
                        guild = JKook.getHttpAPI().getGuild(Guild_ID);
                    }
                    else if(message instanceof TextChannelMessage){
                        guild = ((TextChannelMessage) message).getChannel().getGuild();
                    }

                    //初始化 PageIterator<Set<Role>> roles
                    PageIterator<Set<Role>> roles = null;
                    if(guild != null){
                        roles = guild.getRoles();
                    }

                    //初始化用户信息
                    String user_full_name = sender.getFullName(guild);
                    String want_role;
                    if (!arguments[0].toString().isEmpty()){
                        want_role = arguments[0].toString();
                    }else {
                        want_role = "未提供权限名";
                    }
                    String user_game_name;
                    if (!arguments[2].toString().isEmpty()){
                        user_game_name = arguments[2].toString();
                    }else {
                        user_game_name = "未提供游戏ID";
                    }
                    String qq_number;
                    if (!arguments[1].toString().isEmpty()){
                        qq_number = arguments[1].toString();
                    }else {
                        qq_number = "未提供QQ号";
                    }
                    String user_id = sender.getId();

                    JSONObject info_json = new JSONObject();
                    info_json.set("user_full_name",user_full_name);
                    info_json.set("want_role",want_role);
                    info_json.set("user_game_name",user_game_name);
                    info_json.set("qq_number",qq_number);
                    info_json.set("user_id",user_id);

                    //message.sendToSource(buildCardOne(info_json));

                    Channel channel = JKook.getHttpAPI().getChannel(getConfig().getString("Message_Channel_ID"));
                    if (channel instanceof TextChannel){
                        ((TextChannel)channel).sendComponent(buildCardOne(info_json));
                    }

                }).register(this);
    }

    //方便监听器注册的封装
    private void addListener(Listener listener){
        JKook.getEventManager().registerHandlers(this,listener);
    }

}
