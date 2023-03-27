package moe.wyh2004

import com.baidu.aip.contentcensor.AipContentCensor
import moe.wyh2004.command.BotCensorCommand
import moe.wyh2004.config.DefaultConfig
import moe.wyh2004.event.MsgPreSendCensor
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.utils.info

object BotCensor : KotlinPlugin(
    JvmPluginDescription(
        id = "moe.wyh2004.bot-censor",
        name = "BotCensor",
        version = "0.1.1",
    ) {
        author("WYH2004")
    }
) {
    lateinit var client:AipContentCensor
    override fun onEnable() {
        logger.info { "插件已加载" }
        regConfig()
        initBaiduAip()
        regCommand()
        regListener()
    }

    override fun onDisable() {
        logger.info("插件已卸载")
    }

    fun initBaiduAip(){
        client = AipContentCensor(
            DefaultConfig.App_Id,
            DefaultConfig.App_Key,
            DefaultConfig.Secret_Key
        ).apply {
            setConnectionTimeoutInMillis(2000)
            setSocketTimeoutInMillis(60000)
        }
    }

    fun regConfig(){
        DefaultConfig.reload()
    }

    private fun regListener(){
        GlobalEventChannel.registerListenerHost(MsgPreSendCensor)
    }

    private fun regCommand(){
        BotCensorCommand.register()
    }
}

