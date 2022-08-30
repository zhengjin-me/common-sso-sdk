package me.zhengjin.common.sso.auditing

import cn.dev33.satoken.spring.SpringMVCUtil
import me.zhengjin.common.sso.utils.AuthUtils
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.Optional

/**
 * @version V1.0
 * @title: SpringSecurityAuditorAware
 * @package me.zhengjin.common.sso.core.auditing
 * @description:
 * @date 2019-2-24 18:39
 */
@Component
class SaTokenAuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        return if (SpringMVCUtil.isWeb() && AuthUtils.isLogin()) {
            Optional.of(AuthUtils.currentUser().userName!!)
        } else {
            Optional.of("system")
        }
    }
}
