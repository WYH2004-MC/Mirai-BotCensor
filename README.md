# Mirai-BotCensor
使用百度智能云内容审核API实现Mirai机器人发送消息审核的插件

### 下载&使用 Download & Use
1. 从[Releases](https://github.com/VIPWYH2004/Mirai-BotCensor/releases/)下载最新的插件，并将其安装到Mirai运行环境的`/plugins/`目录下,并启动Mirai  
2. 第一次使用请前往`/config/moe.wyh2004.bot-censor/Config.yml`修改配置文件  
3. 填写[百度智能云内容审核](https://console.bce.baidu.com/ai/#/ai/antiporn/overview/index)提供给你的AppID,AppKey,SecretKey  
4. 完成后重启你的Mirai，或者使用`/botcensor reload`重载配置文件

### 指令 Command
| 指令                | 用途     | 权限                                       |
|-------------------|--------|------------------------------------------|
| /BotCensor info   | 输出插件信息 | moe.wyh2004.bot-censor:command.botcensor |
| /BotCensor reload | 重载配置文件 | moe.wyh2004.bot-censor:command.botcensor |

### 配置文件 Config
```yaml
# 此处填写百度云平台的APP_ID
App_Id: 114514
# 此处填写百度云平台的APP_Key
App_Key: 9191810
# 此处填写百度云平台的SecretKey
Secret_Key: ababababababab
# 号码白名单(列表内的QQ号或者QQ群号将不进行拦截)
QQWhiteList: 
  - 123456789
  - 123456789
  - 123456789
# 消息白名单(列表内的文本在聊天中时将不进行拦截,不区分大小写)
MessageWhiteList: 
  - botcensor
  - hello
  - world
# 审核不通过发出下面的内容
BlockMessage: '*Filtered*'
# 是否对图片进行审核
ImageCensor: true
# 是否在触发拦截的时候在后台输出日志
EnableLogger: true
# 是否在触发拦截的时候在消息中输出拦截原因
BlockInfo: true
```