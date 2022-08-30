package me.zhengjin.common.sso.dto

class ResourceDTO {
    var id: String? = null

    /**
     * 资源名称
     */
    var name: String? = null

    /**
     * 资源代码
     * 不允许修改
     */
    var code: String? = null

    /**
     * 资源类型
     * 0 菜单
     * 1 操作
     * 2 API
     */
    var type: String? = null

    /**
     * type == menu
     * 菜单URL
     */
    var path: String? = null

    /**
     * type == menu
     * 菜单icon名称
     */
    var icon: String? = null

    /**
     * type == menu
     * 在新窗口打开链接
     * _self
     * _blank
     */
    var target: String? = null

    /**
     * 是否报表 默认不是报表
     */
    var report: Boolean? = false

    /**
     * 组件路径
     */
    var component: String? = null

    /**
     * 路由自定义key
     */
    var routerParameter: String? = null
    /**
     * 资源描述
     */
    var description: String? = null

    /**
     * 排序
     */
    var sort: Int? = null

    /**
     * 父元素
     */
    var parentId: String? = null

    /**
     * 所属应用ID
     */
    var applicationId: String? = null

    /**
     * 所属应用代码
     */
    var clientId: String? = null

    /**
     * 所属应用名称
     */
    var applicationName: String? = null
}
