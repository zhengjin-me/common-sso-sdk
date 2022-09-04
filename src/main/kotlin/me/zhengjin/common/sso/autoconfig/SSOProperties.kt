package me.zhengjin.common.sso.autoconfig

import cn.hutool.crypto.asymmetric.SM2
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

class Sm2Key {
    var public: String? = null
    var private: String? = null
    private var _key: SM2? = null

    fun getSM2(): SM2 {
        if (_key == null) {
            _key = SM2(private, public)
        }
        return _key!!
    }
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

    /**
     * 国密SM2非对称加密信息
     * 登陆解密使用
     */
    var loginKey: Sm2Key? = null
}
