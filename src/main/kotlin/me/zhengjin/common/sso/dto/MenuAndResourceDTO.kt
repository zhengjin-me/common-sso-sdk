package me.zhengjin.common.sso.dto

import cn.hutool.core.lang.tree.Tree

/**
 * @date 2021/10/25 11:21
 * @version 1.00
 */
class MenuAndResourceDTO(
    /**
     * 菜单
     */
    var menus: MutableList<Tree<String>>? = null,

    /**
     * 资源code
     */
    var resourceCode: MutableList<String>? = null

)
