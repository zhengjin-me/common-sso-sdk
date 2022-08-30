package me.zhengjin.common.sso.client.vo

import me.zhengjin.common.core.entity.PageableVO

/**
 * 用户管理查询
 */
class UserSearchVO : PageableVO() {
    var ids: List<String>? = null
    var roleId: String? = null
    var roleCode: String? = null
    var tenantId: String? = null
    var tenantDomain: String? = null
    /**
     * 用户名
     */
    var userName: String? = null
    var userNames: List<String>? = null
    /**
     * 昵称
     */
    var nickName: String? = null
}
