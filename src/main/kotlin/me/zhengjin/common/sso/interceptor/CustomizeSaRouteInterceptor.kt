package me.zhengjin.common.sso.interceptor

import cn.dev33.satoken.exception.SaTokenException
import cn.dev33.satoken.exception.StopMatchException
import cn.dev33.satoken.interceptor.SaRouteInterceptor
import cn.dev33.satoken.servlet.model.SaRequestForServlet
import cn.dev33.satoken.servlet.model.SaResponseForServlet
import cn.dev33.satoken.stp.StpUtil
import me.zhengjin.common.core.entity.HttpResult
import me.zhengjin.common.core.exception.ServiceException
import me.zhengjin.common.sso.exception.AuthorityExceptions
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomizeSaRouteInterceptor : SaRouteInterceptor() {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        // 如果未提供function，默认进行登录验证
        if (function == null) {
            if (!StpUtil.isLogin()) {
                response.status = HttpStatus.OK.value()
                response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
                response.writer.print(
                    HttpResult.fail<String>(
                        ServiceException.Exceptions.UNAUTHORIZED.message,
                        ServiceException.Exceptions.UNAUTHORIZED.code
                    ).toString()
                )
                return false
            }
        } else {
            // 否则执行认证函数
            try {
                function.run(SaRequestForServlet(request), SaResponseForServlet(response), handler)
            } catch (e: Exception) {
                return when (e) {
                    // 停止匹配，进入Controller
                    is StopMatchException -> true
                    else -> {
                        // 停止匹配，向前端输出结果
                        val httpResult: HttpResult<String> = if (e is SaTokenException) {
                            val serviceException = AuthorityExceptions.handleSaTokenException(e)
                            HttpResult.fail(serviceException.message, serviceException.type.code)
                        } else {
                            HttpResult.fail(e.message, 500)
                        }
                        response.status = HttpStatus.OK.value()
                        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
                        response.writer.print(httpResult)
                        false
                    }
                }
            }
        }
        // 通过验证
        return true
    }
}
