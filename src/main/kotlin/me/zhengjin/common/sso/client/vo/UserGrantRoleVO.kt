package me.zhengjin.common.sso.client.vo

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class UserGrantRoleVO {
    @NotBlank(message = "用户不能为空")
    var userId: String? = null

    @NotNull(message = "角色不能为空", groups = [Grant::class])
    @Size(min = 1, message = "请至少选择一个角色")
    var roleIds: List<String>? = null

    /**
     *
     */
    @NotNull(message = "角色代码不能为空", groups = [Append::class])
    @Size(min = 1, message = "请至少追加一个角色")
    var codes: List<String>? = null
}

/**
 * 授予
 */
interface Grant

/**
 * 追加
 */
interface Append
