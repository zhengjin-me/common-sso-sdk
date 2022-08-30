# common-sso-core

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/zhengjin-me/common-sso-core/Gradle%20Package?style=flat-square)
[![Maven Central](https://img.shields.io/maven-central/v/me.zhengjin/common-sso-core.svg?style=flat-square&color=brightgreen)](https://maven-badges.herokuapp.com/maven-central/me.zhengjin/common-sso-core/)
![GitHub](https://img.shields.io/github/license/zhengjin-me/common-sso-core?style=flat-square)

```
dependencies {
    implementation "me.zhengjin:common-sso-core:version"
}
```

### 配置示例

---

#### 注册拦截器
```kotlin
package me.zhengjin.sso.config

import cn.dev33.satoken.exception.BackResultException
import cn.dev33.satoken.exception.StopMatchException
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor
import cn.dev33.satoken.interceptor.SaRouteInterceptor
import cn.dev33.satoken.servlet.model.SaRequestForServlet
import cn.dev33.satoken.servlet.model.SaResponseForServlet
import me.zhengjin.common.core.entity.HttpResult
import me.zhengjin.sso.utils.AuthUtils
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        // 注册登录拦截器，并排除登录接口或其他可匿名访问的接口地址 (与注解拦截器无关)
        registry.addInterceptor(object : SaRouteInterceptor() {
            override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

                // 如果未提供function，默认进行登录验证
                if (function == null) {
                    AuthUtils.saUtil().checkLogin()
                } else {
                    // 否则执行认证函数
                    try {
                        function.run(SaRequestForServlet(request), SaResponseForServlet(response), handler)
                    } catch (e: StopMatchException) {
                        // 停止匹配，进入Controller
                    } catch (e: BackResultException) {
                        // 停止匹配，向前端输出结果
                        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
                        response.writer.print(HttpResult.fail<String>(e.message, 500).toString())
                        return false
                    }
                }

                // 通过验证
                return true
            }
        }).addPathPatterns("/**").excludePathPatterns("/user/login")
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(SaAnnotationInterceptor()).addPathPatterns("/**")
    }
}
```