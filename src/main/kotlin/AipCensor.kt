package moe.wyh2004

import com.baidu.aip.contentcensor.AipContentCensor
import com.baidu.aip.contentcensor.EImgType
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.MiraiLogger
import org.json.JSONObject


/**
 * @author WYH2004
 * @date 2023/3/22
 **/
object AipCensor {
    private var logger : MiraiLogger = BotCensor.logger
    suspend fun imageCensor(client: AipContentCensor, img: Image) : JSONObject{
        val res = client.imageCensorUserDefined(img.queryUrl(), EImgType.URL, null)
        logger.debug(res.toString())
        return res
    }

    fun textCensor(client: AipContentCensor, text: PlainText) : JSONObject{
        val res : JSONObject = client.textCensorUserDefined(text.toString())
        logger.debug(res.toString())
        return res
    }
}