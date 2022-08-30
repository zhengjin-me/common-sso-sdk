package me.zhengjin.common.sso.dto

class RoleDTO {
    var id: String? = null

    /**
     * 角色名称
     */
    var name: String? = null

    /**
     * 角色代码
     */
    var code: String? = null

    /**
     * 资源描述
     */
    var description: String? = null

    /**
     * 所属租户ID
     */
    var tenantId: String? = null

    /**
     * 所属租户名称
     */
    var tenantName: String? = null
}
