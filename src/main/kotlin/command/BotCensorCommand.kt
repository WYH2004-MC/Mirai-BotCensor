package moe.wyh2004.command

import moe.wyh2004.BotCensor
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.plugin.version
import net.mamoe.mirai.utils.MiraiLogger

/**
 * @author WYH2004
 * @date 2023/3/22
 **/
object BotCensorCommand : CompositeCommand(
    owner = BotCensor,
    primaryName = "BotCensor",
    description = "BotCensor"
){
    private var logger : MiraiLogger = BotCensor.logger

    @SubCommand("reload","重载")
    suspend fun CommandSender.reload() {
        try {
            BotCensor.regConfig()
            BotCensor.initBaiduAip()
            sendMessage("已重载BotCensor配置文件!")
        } catch (ex: Exception) {
            logger.error(ex)
        }
    }

    @SubCommand("info","信息")
    suspend fun CommandSender.info() {
        val pluginInfo =
                    "=== BotCensor ===\n" +
                    "版本：${BotCensor.version}\n" +
                    "作者: WYH2004\n" +
                    "项目地址:\n" +
                    "https://github.com/VIPWYH2004/Mirai-BotCensor"
        sendMessage(pluginInfo)
    }
}
