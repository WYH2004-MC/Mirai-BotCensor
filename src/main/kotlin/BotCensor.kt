package moe.wyh2004

import com.baidu.aip.contentcensor.AipContentCensor
import kotlinx.serialization.json.JsonPrimitive
import moe.wyh2004.command.BotCensorCommand
import moe.wyh2004.config.DefaultConfig
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.MessagePreSendEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.toMessageChain
import net.mamoe.mirai.utils.info
import org.json.JSONObject
import java.util.*

object BotCensor : KotlinPlugin(
    JvmPluginDescription(
        id = "moe.wyh2004.bot-censor",
        name = "BotCensor",
        version = "0.1.0",
    ) {
        author("WYH2004")
    }
) {
    lateinit var client:AipContentCensor
    override fun onEnable() {
        logger.info { "插件已加载" }
        regCommand()
        regConfig()
        initBaiduAip()

        GlobalEventChannel.subscribeAlways<MessagePreSendEvent> {
            val lowerCaseMessage = message.toString().lowercase(Locale.getDefault())
            if (!DefaultConfig.QQWhiteList.contains(target.id) && !DefaultConfig.MessageWhiteList.any { lowerCaseMessage.contains(it.lowercase(Locale.getDefault())) }){
                for (msg in message.toMessageChain()) {
                    var resultType = false
                    val result = when (msg) {
                        is PlainText -> AipCensor.textCensor(client,msg)
                        is Image -> {
                            if(DefaultConfig.ImageCensor){
                                AipCensor.imageCensor(client,msg)
                            }else{
                                JSONObject(mapOf("conclusion" to JsonPrimitive("跳过")))
                            }
                        }
                        else -> JSONObject(mapOf("conclusion" to JsonPrimitive("错误的消息类型")))
                    }
                    if (result.get("conclusion").equals("不合规")){
                        resultType = true
                        val reason = result.getJSONArray("data").getJSONObject(0).get("msg")
                        if (DefaultConfig.EnableLogger){
                            logger.info("机器人尝试输出:\n$message\n已被拦截,拦截原因:$reason")
                        }
                        message = if (DefaultConfig.BlockInfo) {
                            PlainText(DefaultConfig.BlockMessage + "\n拦截原因:$reason")
                        }else{
                            PlainText(DefaultConfig.BlockMessage)
                        }
                    }
                    if (resultType) break
                }
            }
        }
    }

    override fun onDisable() {
        logger.info("插件已卸载")
    }

    fun initBaiduAip(){
        client = AipContentCensor(DefaultConfig.App_Id, DefaultConfig.App_Key, DefaultConfig.Secret_Key)
        client.setConnectionTimeoutInMillis(2000)
        client.setSocketTimeoutInMillis(60000)
    }

    fun regConfig(){
        DefaultConfig.reload()
    }

    fun regCommand(){
        BotCensorCommand.register()
    }
}

