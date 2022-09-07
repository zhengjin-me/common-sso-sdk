package me.zhengjin.common.sso.autoconfig

import cn.dev33.satoken.stp.StpInterface
import me.zhengjin.common.sso.client.SSOInnerApiClient
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@ConditionalOnExpression("'center'.equalsIgnoreCase('\${customize.sso.model:center}')")
@AutoConfigureAfter(SSOPropertiesConfiguration::class)
@ConditionalOnClass(SSOInnerApiClient::class)
class SSOAutoConfiguration(
    private val ssoApiClient: SSOInnerApiClient
) {

    @Bean
    @ConditionalOnClass(StpInterface::class)
    @ConditionalOnMissingBean(StpInterface::class)
    fun stpInterface(): StpInterface {
        return object : StpInterface {
            override fun getPermissionList(loginId: Any?, loginType: String?): List<String> {
                val userResource = ssoApiClient.userResourceSearch(loginId as String)
                if (userResource.isOk) {
                    return userResource.body ?: listOf()
                }
                return listOf()
            }

            override fun getRoleList(loginId: Any?, loginType: String?): List<String> {
                val userRoles = ssoApiClient.userRoleSearch(loginId as String)
                if (userRoles.isOk) {
                    return userRoles.body ?: listOf()
                }
                return listOf()
            }
        }
    }
}
