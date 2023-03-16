package com.xiaoACE.PMPlugin.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import snw.jkook.entity.abilities.Accessory;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.ButtonElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ActionGroupModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;
import snw.jkook.message.component.card.structure.Paragraph;

import java.util.Arrays;

public class allTheCard {

    //构建待审核卡片
    public static MultipleCardComponent buildCardOne(JSONObject info_json){

        String user_full_name = info_json.getStr("user_full_name","傻逼！");
        String want_role = info_json.getStr("want_role","未提供想要的权限");
        String user_game_name = info_json.getStr("user_game_name","未提供游戏ID");
        String qq_number = info_json.getStr("qq_number","未提供QQ号");

        CardBuilder cardBuilder = new CardBuilder();
        cardBuilder.setTheme(Theme.INFO).setSize(Size.LG);

        //申请人
        String a = "**申请人**\n{}";
        a = StrUtil.format(a,user_full_name);

        //QQ
        String b = "**QQ**\n{}";
        b = StrUtil.format(b,qq_number);

        //权限名
        String c = "**权限名**\n{}";
        c = StrUtil.format(c,want_role);

        //游戏ID
        String d = "**游戏ID**\n{}";
        d = StrUtil.format(d,user_game_name);


        //添加头部
        cardBuilder.addModule(new HeaderModule(new PlainTextElement("审核信息(未处理)")));

        cardBuilder.addModule(new SectionModule(new Paragraph(3,
                Arrays.asList(
                        new MarkdownElement(a),
                        new MarkdownElement(b),
                        new MarkdownElement(c))),null, Accessory.Mode.RIGHT));
        cardBuilder.addModule(new SectionModule(new Paragraph(3,
                Arrays.asList(
                        new MarkdownElement(d),
                        new MarkdownElement(""),
                        new MarkdownElement(""))),null, Accessory.Mode.RIGHT));

        cardBuilder.addModule(
                new ActionGroupModule(
                        Arrays.asList(
                                new ButtonElement(Theme.PRIMARY,
                                        info_json.set("action-name","通过").toString(),
                                        ButtonElement.EventType.RETURN_VAL,
                                        new PlainTextElement("通过")),
                                new ButtonElement(Theme.DANGER,
                                        info_json.set("action-name","不通过").toString(),
                                        ButtonElement.EventType.RETURN_VAL,
                                        new PlainTextElement("不通过"))
                        )
                )
        );


        return cardBuilder.build();
    }

    //审核完成卡片
    public static MultipleCardComponent buildCardTwo(JSONObject info_json){

        String user_full_name = info_json.getStr("user_full_name","傻逼！");
        String want_role = info_json.getStr("want_role","未提供想要的权限");
        String user_game_name = info_json.getStr("user_game_name","未提供游戏ID");
        String qq_number = info_json.getStr("qq_number","未提供QQ号");
        String flag = info_json.getStr("action-name","Error");


        CardBuilder cardBuilder = new CardBuilder();
        cardBuilder.setTheme(Theme.DANGER).setSize(Size.LG);

        //申请人
        String a = "**申请人**\n{}";
        a = StrUtil.format(a,user_full_name);

        //QQ
        String b = "**QQ**\n{}";
        b = StrUtil.format(b,qq_number);

        //权限名
        String c = "**权限名**\n{}";
        c = StrUtil.format(c,want_role);

        //游戏ID
        String d = "**游戏ID**\n{}";
        d = StrUtil.format(d,user_game_name);

        //通过情况
        // "**(font)审核状态: (font)[primary]** **(font)通过(font)[success]**"
        // "**(font)审核状态: (font)[primary]** **(font)不通过(font)[danger]**"
        String e = "**(font)审核状态: (font)[primary]** **(font){}(font)[{}]**";

        if (flag.equals("通过")){

            e = StrUtil.format(e,flag,"success");

        }else if (flag.equals("不通过")){

            e = StrUtil.format(e,flag,"danger");

        }else {

            e = StrUtil.format(e,"Error","warning");

        }

        //添加头部
        cardBuilder.addModule(new HeaderModule(new PlainTextElement("审核信息(已处理)")));

        cardBuilder.addModule(new SectionModule(new Paragraph(3,
                Arrays.asList(
                        new MarkdownElement(a),
                        new MarkdownElement(b),
                        new MarkdownElement(c))),null, Accessory.Mode.RIGHT));
        cardBuilder.addModule(new SectionModule(new Paragraph(3,
                Arrays.asList(
                        new MarkdownElement(d),
                        new MarkdownElement(""),
                        new MarkdownElement(""))),null, null));

        cardBuilder.addModule(new SectionModule(new MarkdownElement(e),null,null));

        return cardBuilder.build();
    }
}
