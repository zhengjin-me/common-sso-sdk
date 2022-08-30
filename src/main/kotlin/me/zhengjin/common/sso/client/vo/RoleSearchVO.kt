package me.zhengjin.common.sso.client.vo

import me.zhengjin.common.core.entity.PageableVO

class RoleSearchVO : PageableVO() {
    var ids: List<String>? = null
    var userId: String? = null
    var userName: String? = null
    var tenantId: String? = null
    var tenantDomain: String? = null

    var name: String? = null

    var code: String? = null
    var codes: List<String>? = null

    /**
     * 多个以 ， 分割
     */
    var paramKey: String? = null

    /**
     * 多个以，分割
     */
    var paramValue: String? = null
}
