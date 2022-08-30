package me.zhengjin.common.sso.client.vo

import javax.validation.constraints.NotBlank

/**
 * 企业参数vo
 */
class EnterpriseParamVO {

    /**
     * 企业id
     */
    @NotBlank(message = "企业code不能为空")
    var enterpriseCode: String? = null

    /**
     * json对应的key
     */
    @NotBlank(message = "json对应的key不能为空")
    var paramKey: String? = null

    /**
     * json对应的value
     */
    @NotBlank(message = "json对应的value不能为空")
    var paramValue: String? = null
}
