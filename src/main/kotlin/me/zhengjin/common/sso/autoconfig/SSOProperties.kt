package me.zhengjin.common.sso.autoconfig

import org.springframework.boot.context.properties.ConfigurationProperties

class SSOClient {
    /**
     * 应用代码
     */
    var clientId: String? = null

    /**
     * 应用秘钥
     */
    var secret: String? = null
    var default: Boolean = false
}

enum class SSOModel {
    STANDALONE,
    CENTER
}

@ConfigurationProperties(prefix = "customize.sso")
class SSOProperties {

    /**
     * 服务模式
     * standalone 独立模式
     * center     单点中心(default)
     */
    var model: SSOModel = SSOModel.CENTER

    /**
     * 单点中心系统地址
     */
    var url: String? = null
    var clients: List<SSOClient> = ArrayList()
}
