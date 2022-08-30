package me.zhengjin.common.sso.dto

import cn.hutool.core.util.DesensitizedUtil
import cn.hutool.core.util.StrUtil
import java.util.Date

class UserDetailDTO(
    /**
     * 用户ID
     */
    var id: String? = null,
    /**
     * 租户名称
     */
    var tenantId: String? = null,
    var tenantName: String? = null,
    /**
     * 企业名称
     */
    var enterpriseId: String? = null,
    var enterpriseCode: String? = null,
    var enterpriseName: String? = null,
    /**
     * 用户名
     */
    var userName: String? = null,
    /**
     * 昵称
     */
    var nickName: String? = null,
    /**
     * 头像地址
     */
    var avatar: String? = null,
    /**
     * 邮箱
     */
    var email: String? = null,
    /**
     * 手机
     */
    var mobile: String? = null,
    /**
     * 生日
     */
    var birthday: Date? = null,
    /**
     * 性别
     */
    var sex: Int? = null,

    /**
     * 创建时间
     */
    var createdTime: Date? = null,
) {
    /**
     * 获取脱敏后的基础数据
     */
    fun desensitize(): UserDetailDTO {
        return UserDetailDTO(
            id = id,
            userName = userName,
            nickName = nickName,
            avatar = avatar,
            email = DesensitizedUtil.email(email),
            mobile = if (mobile.isNullOrBlank()) null else StrUtil.hide(mobile, 0, mobile!!.length - 4),
            birthday = birthday,
            sex = sex,
            enterpriseCode = enterpriseCode,
            enterpriseName = enterpriseName,
            createdTime = createdTime
        )
    }
}
