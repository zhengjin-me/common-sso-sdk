package me.zhengjin.common.sso.exception

import cn.dev33.satoken.exception.DisableLoginException
import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.NotPermissionException
import cn.dev33.satoken.exception.NotRoleException
import cn.dev33.satoken.exception.NotSafeException
import cn.dev33.satoken.exception.SaTokenException
import me.zhengjin.common.core.exception.ServiceException

object AuthorityExceptions {
    val NOT_TOKEN = ServiceException.ExceptionType(10050, "用户未登录")
    val INVALID_TOKEN = ServiceException.ExceptionType(10051, "无效的凭证")
    val TOKEN_TIMEOUT = ServiceException.ExceptionType(10052, "登陆已过期")
    val BE_REPLACED = ServiceException.ExceptionType(10053, "您已在其他设备登陆")
    val KICK_OUT = ServiceException.ExceptionType(10054, "您已被系统登出")
    val NOT_TWO_SAFE = ServiceException.ExceptionType(10055, "安全认证失败")
    val DISABLE_LOGIN = ServiceException.ExceptionType(10056, "此账号已被封禁")

    fun handleSaTokenException(e: SaTokenException): ServiceException {
        return when (e) {
            is NotLoginException -> {
                when (e.type) {
                    NotLoginException.NOT_TOKEN -> ServiceException(type = NOT_TOKEN, message = "用户未登录")
                    NotLoginException.INVALID_TOKEN -> ServiceException(type = INVALID_TOKEN, message = "无效的凭证")
                    NotLoginException.TOKEN_TIMEOUT -> ServiceException(type = TOKEN_TIMEOUT, message = "登陆已过期")
                    NotLoginException.BE_REPLACED -> ServiceException(type = BE_REPLACED, message = "您已在其他设备登陆")
                    NotLoginException.KICK_OUT -> ServiceException(type = KICK_OUT, message = "您已被系统登出")
                    else -> ServiceException(type = NOT_TOKEN, message = "用户未登录")
                }
            }
            is DisableLoginException -> ServiceException(type = DISABLE_LOGIN, message = "此账号已被封禁")
            is NotPermissionException, is NotRoleException -> ServiceException(type = ServiceException.Exceptions.FORBIDDEN, message = "未授权的操作")
            is NotSafeException -> ServiceException(type = NOT_TWO_SAFE, message = "二级认证失败")
            else -> ServiceException(type = NOT_TOKEN, message = "认证失败")
        }
    }
}
