package me.zhengjin.common.sso.dto

class ApplicationDTO(
    var id: String? = null,
    /**
     * 应用名称
     */
    var name: String? = null,
    /**
     * 应用图标(URL)
     */
    var icon: String? = null,
    /**
     * 应用代码
     */
    var code: String? = null,
    /**
     * 应用秘钥
     */
    var secret: String? = null,
    /**
     * 回调地址
     */
    var callback: String? = null,
    /**
     * 应用描述
     */
    var description: String? = null,
)
