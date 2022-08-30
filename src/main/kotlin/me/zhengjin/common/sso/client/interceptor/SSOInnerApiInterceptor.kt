package me.zhengjin.common.sso.client.interceptor

import cn.dev33.satoken.context.SaHolder
import cn.hutool.core.codec.Base64
import cn.hutool.crypto.digest.DigestUtil
import com.dtflys.forest.http.ForestRequest
import com.dtflys.forest.http.ForestRequestType
import com.dtflys.forest.interceptor.Interceptor
import me.zhengjin.common.core.entity.HttpResult
import me.zhengjin.common.core.exception.ServiceException
import me.zhengjin.common.core.utils.JacksonSerializeUtils
import me.zhengjin.common.sso.autoconfig.SSOProperties
import me.zhengjin.common.sso.utils.AuthUtils
import me.zhengjin.common.utils.SpringBeanUtils

class SSOInnerApiInterceptor : Interceptor<HttpResult<*>> {
    companion object {
        private const val EMPTY = ""
    }

    private val objectMapper = JacksonSerializeUtils.defaultJacksonConfig()

    override fun beforeExecute(request: ForestRequest<*>): Boolean {
        // InnerApi仅支持POST
        ServiceException.requireTrue(request.type == ForestRequestType.POST) { "Request method '${request.type.name}' not supported" }
        var clientId: String? = null
        var secret: String? = null
        val ssoProperties = SpringBeanUtils.getBean(SSOProperties::class.java)
        val requestClientId: String? = SaHolder.getRequest().getHeader(AuthUtils.CLIENT_ID) ?: SaHolder.getRequest().getParam(AuthUtils.CLIENT_ID) ?: null
        // 如果请求中没有找到clientId, 尝试使用默认配置
        if (requestClientId.isNullOrBlank()) {
            val defaultClient = ssoProperties.clients.firstOrNull { it.default }
            ServiceException.requireNotNull(defaultClient) { "默认单点客户端配置未找到" }
            clientId = defaultClient!!.clientId!!
            secret = defaultClient.secret!!
        } else {
            ssoProperties.clients.forEach {
                if (it.clientId == requestClientId) {
                    clientId = it.clientId!!
                    secret = it.secret!!
                }
            }
        }
        // 如果请求与默认配置均无法找到 抛出异常
        ServiceException.requireNotNullOrBlank(clientId) { "应用代码缺失" }
        ServiceException.requireNotNullOrBlank(secret) { "应用秘钥缺失" }

        val jsonData = if (!request.arguments.isNullOrEmpty()) {
            val data = request.getArgument(0)
            objectMapper.writeValueAsString(data)
        } else EMPTY

        val timestamp = System.currentTimeMillis().toString()
        val encodeBase64String = if (jsonData.isNullOrBlank()) EMPTY else Base64.encode(jsonData)
        val waitSigningData = "$clientId#$timestamp#$encodeBase64String#$secret"
        val signature = DigestUtil.md5Hex(waitSigningData.toByteArray())

        request.addHeader("clientId", clientId)
        request.addHeader("timestamp", timestamp)
        request.addHeader("signature", signature)
        request.addBody(jsonData)
        return true
    }
}
