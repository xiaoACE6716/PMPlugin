# PMPlugin

这是一个简单的使用命令发起Kook服务器角色申请工单的机器人\
为鲤鱼喵喵服务器开发！

基于JKook api
<p>
    <a  href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8+-green.svg"  alt=""/>
	</a>
    <a href= "https://github.com/SNWCreations/JKook">
        <img src="https://img.shields.io/badge/-JKook-red" alt="">
    </a>
    <a href= "https://github.com/SNWCreations/KookBC">
        <img src="https://img.shields.io/badge/-KookBC-red" alt="">
    </a>
</p>

## 插件用法

将该插件放到JKook API的实现客户端(比如KookBC)的plugins文件夹里,启动机器人,将会生成配置文件，按配置文件填写好相应信息，重启机器人即可开始使用。

该插件提供的命令: `/申请权限 [角色名] [QQ号] [游戏ID]` \
其中,角色名必须提供(对应Kook服务器里的角色名)\
QQ号与游戏ID为可选选项,但必须按顺序提供\

注意，机器人给用户上角色的时候采用`角色名`匹配,所以使用命令时角色名必须要对的上服务器里存在的角色名，否则就不会给用户上角色

## 以下是示例图片

<img src="https://img.kookapp.cn/assets/2023-03/5oTE2e81KK0al05c.png">
<img src="https://img.kookapp.cn/assets/2023-03/gRADS2qPVv0b302s.png">
<img src="https://img.kookapp.cn/assets/2023-03/rA53ghQJUC0g507f.png">


