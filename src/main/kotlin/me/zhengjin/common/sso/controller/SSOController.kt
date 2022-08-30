package me.zhengjin.common.sso.controller

import cn.dev33.satoken.sso.SaSsoUtil
import cn.dev33.satoken.stp.StpUtil
import me.zhengjin.common.core.entity.HttpResult
import me.zhengjin.common.core.exception.ServiceException
import me.zhengjin.common.sso.client.SSOInnerApiClient
import me.zhengjin.common.sso.client.vo.RoleSearchVO
import me.zhengjin.common.sso.dto.MenuAndResourceDTO
import me.zhengjin.common.sso.dto.RoleListDTO
import me.zhengjin.common.sso.dto.UserDetailDTO
import me.zhengjin.common.sso.utils.AuthUtils
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/sso"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
@ConditionalOnExpression("'center'.equalsIgnoreCase('\${customize.sso.model:center}')")
class SSOController(
    private val ssoInterApiClient: SSOInnerApiClient
) {

    @PostMapping("/ticket/check")
    fun loginByTicket(@RequestBody ticket: String?): HttpResult<String> {
        ServiceException.requireNotNullOrBlank(ticket) { "无效的凭证,请重新登录!" }
        val loginId = SaSsoUtil.checkTicket(ticket)
        val token = StpUtil.getTokenValueByLoginId(loginId) ?: throw ServiceException(type = ServiceException.Exceptions.UNAUTHORIZED)
        return HttpResult.ok(body = token)
    }

    @PostMapping("/logout")
    fun logout(): HttpResult<String> {
        AuthUtils.logout()
        return HttpResult.ok("登出成功")
    }

    /**
     * 当前用户信息
     */
    @PostMapping("/current")
    fun current(): HttpResult<UserDetailDTO> {
        return HttpResult.ok(AuthUtils.currentUser().desensitize())
    }

    @PostMapping("/menus")
    fun userMenuAndResource(): HttpResult<MenuAndResourceDTO> {
        return ssoInterApiClient.userMenuAndResourceSearch(AuthUtils.currentUser().id!!)
    }

    @PostMapping("/roles")
    fun getUserRoles(): HttpResult<List<String>> {
        return ssoInterApiClient.userRoleSearch(AuthUtils.currentUser().id!!)
    }

    /**
     * 当前用户资源code
     */
    @PostMapping("/resource")
    fun userResources(): HttpResult<List<String>> {
        return ssoInterApiClient.userResourceSearch(AuthUtils.currentUser().id!!)
    }
    /**
     * 当前应用的iconUrl
     */
    @PostMapping("/application/icon")
    fun getIconUrl(): HttpResult<String> {
        return ssoInterApiClient.getIconUrl()
    }

    /**
     * 获取角色列表
     */
    @PostMapping("/getRoleList")
    fun getRoleList(@RequestBody data: RoleSearchVO): HttpResult<List<RoleListDTO>> {
        return ssoInterApiClient.getRoleList(data)
    }
}
