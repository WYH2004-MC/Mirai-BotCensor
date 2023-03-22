package moe.wyh2004.config


import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * @author WYH2004
 * @date 2023/3/22
 **/
object DefaultConfig : AutoSavePluginConfig("Config"){
    @ValueDescription("此处填写百度云平台的APP_ID")
    val App_Id: String by value("114514")

    @ValueDescription("此处填写百度云平台的APP_Key")
    val App_Key: String by value("9191810")

    @ValueDescription("此处填写百度云平台的SecretKey")
    val Secret_Key: String by value("ababababababab")

    @ValueDescription("审核白名单(列表内的QQ号或者QQ群号将不进行拦截)")
    val WhiteList: List<Long> by value(listOf(123456789,123456789,123456789))

    @ValueDescription("审核不通过发出下面的内容")
    val BlockMessage:String by value("*Filtered*")

    @ValueDescription("是否在触发拦截的时候在后台输出日志")
    val EnableLogger:Boolean by value(true)

    @ValueDescription("是否在触发拦截的时候在消息中输出拦截原因")
    val BlockInfo:Boolean by value(true)

}