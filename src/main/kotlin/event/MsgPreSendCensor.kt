package moe.wyh2004.event

import moe.wyh2004.AipCensor
import moe.wyh2004.BotCensor
import moe.wyh2004.config.DefaultConfig
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.events.MessagePreSendEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.toMessageChain
import org.json.JSONException
import java.util.*

/**
 * @author WYH2004
 * @date 2023/3/24
 **/
object MsgPreSendCensor : ListenerHost {
    @EventHandler
    suspend fun MessagePreSendEvent.onEvent(){
        val lowerCaseMessage = message.toString().lowercase(Locale.getDefault())
        if ((!DefaultConfig.QQWhiteList.contains(target.id) && !DefaultConfig.MessageWhiteList.any { lowerCaseMessage.contains(it.lowercase(Locale.getDefault())) }) || DefaultConfig.MessageBlackList.any { lowerCaseMessage.contains(it.lowercase(Locale.getDefault())) }){
            for (msg in message.toMessageChain()) {
                BotCensor.logger.info("msg: $msg")
                var resultType = false
                val result = when (msg) {
                    is PlainText -> AipCensor.textCensor(BotCensor.client,msg)
                    is Image -> {
                        if(DefaultConfig.ImageCensor){
                            AipCensor.imageCensor(BotCensor.client,msg)
                        }else continue
                    }
                    else -> continue
                }
                try {
                    if (result.get("conclusion").equals("不合规")){
                        resultType = true
                        val reason = result.getJSONArray("data").getJSONObject(0).get("msg")
                        if (DefaultConfig.EnableLogger){
                            BotCensor.logger.info("机器人尝试输出:\n$message\n已被拦截,拦截原因:$reason")
                        }
                        message = if (DefaultConfig.BlockInfo) {
                            PlainText(DefaultConfig.BlockMessage + "\n拦截原因:$reason")
                        }else{
                            PlainText(DefaultConfig.BlockMessage)
                        }
                    }
                }catch (e : JSONException){
                    BotCensor.logger.error("百度API返回数据有误，该消息未经过审核")
                    BotCensor.logger.error(e)
                    BotCensor.logger.error("result: $result")
                }
                if (resultType) break
            }
        }
    }
}