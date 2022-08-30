package me.zhengjin.common.sso.utils

import cn.dev33.satoken.context.SaHolder
import cn.dev33.satoken.stp.StpUtil
import me.zhengjin.common.core.exception.ServiceException
import me.zhengjin.common.sso.client.SSOInnerApiClient
import me.zhengjin.common.sso.dto.UserDetailDTO
import me.zhengjin.common.utils.SpringBeanUtils

object AuthUtils {

    const val CLIENT_ID = "clientId"

    // 当前用户
    const val CURRENT_USER = "current_user"
    private val ssoApiClient = SpringBeanUtils.getBean(SSOInnerApiClient::class.java)
    fun currentClientId(): String {
        val clientId = SaHolder.getRequest().getHeader(CLIENT_ID) ?: SaHolder.getRequest().getParam(CLIENT_ID) ?: null
        ServiceException.requireNotNull(clientId) { "非法参数" }
        return clientId!!.lowercase()
    }

    /**
     * 是否为客户端
     */
    fun isClient() = currentClientId().startsWith("c:", true)

    /**
     * 是否为管理端
     */
    fun isManager() = currentClientId().startsWith("m:", true)

    /**
     * 是否登录
     * 如果未提供系统标识, 视为未登录
     */
    fun isLogin(): Boolean = StpUtil.isLogin()

    /**
     * 登出
     */
    fun logout() = StpUtil.logout()

    /**
     * 踢人下线
     */
    fun logoutByUserId(userId: String) = StpUtil.kickout(userId)

    /**
     * 是否拥有指定角色
     */
    fun hasRole(roleCode: String): Boolean {
        val userRoles = ssoApiClient.userRoleSearch(currentUser().id!!)
        if (userRoles.isOk) {
            return userRoles.body?.contains(roleCode) ?: false
        } else {
            throw ServiceException(message = "用户权限获取失败", printStack = false)
        }
    }

    /**
     * 是否为超级管理员
     */
    fun isSuperAdmin() = hasRole("superAdmin")

    /**
     * 当前用户信息
     */
    fun currentUser(): UserDetailDTO = StpUtil.getSession().getModel(CURRENT_USER, UserDetailDTO::class.java)

    /**
     * 当前用户对应的企业扩展参数  json格式
     */
    fun entParameter(): String? {
        val result = ssoApiClient.entParameter(currentUser().enterpriseCode!!)
        return if (result.isOk) {
            result.body
        } else null
    }
}
