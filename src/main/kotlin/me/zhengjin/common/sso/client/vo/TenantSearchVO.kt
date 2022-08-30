package me.zhengjin.common.sso.client.vo

import me.zhengjin.common.core.entity.PageableVO

class TenantSearchVO : PageableVO() {
    var ids: List<String>? = null
    var userId: String? = null
    var userName: String? = null
    var roleId: String? = null
    var roleCode: String? = null

    var name: String? = null
    var domain: String? = null
    var disable: Boolean? = null
}
