package me.zhengjin.common.sso.dto

import cn.hutool.core.lang.tree.Tree
import cn.hutool.core.lang.tree.TreeNodeConfig
import cn.hutool.core.lang.tree.TreeUtil
import me.zhengjin.common.sso.utils.AuthUtils

class UserMenuDTO(
    var id: String? = null,

    /**
     * 资源名称
     */
    var name: String? = null,

    /**
     * 资源代码
     * 不允许修改
     */
    var code: String? = null,
    /**
     * type == menu
     * 菜单URL
     */
    var path: String? = null,
    /**
     * type == menu
     * 菜单icon名称
     */
    var icon: String? = null,
    /**
     * type == menu
     * 在新窗口打开链接
     * _self
     * _blank
     */
    var target: String? = "0",

    /**
     * 是否报表 : false 不是, true 是
     */
    var report: Boolean? = false,
    /**
     * 组件路径
     */
    var component: String? = null,
    /**
     * 路由自定义key
     */
    var routerParameter: String? = null,
    /**
     * 资源描述
     */
    var description: String? = null,

    /**
     * 排序
     */
    var sort: Int? = null,

    /**
     * 父元素
     */
    var parentId: String? = null,
    /**
     * 所属应用ID
     */
    var applicationId: String? = null,

    /**
     * 所属应用代码
     */
    var clientId: String? = null,

    /**
     * 所属应用名称
     */
    var applicationName: String? = null,
)

fun List<ResourceDTO>.toUserMenuDTO(): MutableList<UserMenuDTO> {
    return this.filter { it.type == "0" }.map {
        UserMenuDTO(
            id = it.id,
            name = it.name,
            code = it.code,
            path = it.path,
            icon = it.icon,
            target = it.target,
            sort = it.sort,
            report = it.report,
            component = it.component,
            routerParameter = it.routerParameter,
            parentId = it.parentId,
            applicationId = it.applicationId,
            clientId = it.clientId,
            applicationName = it.applicationName,
        )
    }.toMutableList()
}

fun List<UserMenuDTO>.toTree(clientId: String = AuthUtils.currentClientId()): MutableList<Tree<String>> {
    if (this.isEmpty()) return mutableListOf()

    val config = TreeNodeConfig()
    config.weightKey = "sort"

    val list = this.filter { it.clientId.equals(clientId) }
    if (list.isEmpty()) return mutableListOf()

    return TreeUtil.build(list, null, config) { treeNode, tree ->
        tree.id = treeNode.id
        tree.parentId = treeNode.parentId
        tree.weight = treeNode.sort
        tree.name = treeNode.name
        tree.putExtra("path", treeNode.path)
        tree.putExtra("icon", treeNode.icon)
        tree.putExtra("code", treeNode.code)
        tree.putExtra("target", treeNode.target)
        tree.putExtra("report", treeNode.report)
        tree.putExtra("component", treeNode.component)
        tree.putExtra("routerParameter", treeNode.routerParameter)
    }
}
