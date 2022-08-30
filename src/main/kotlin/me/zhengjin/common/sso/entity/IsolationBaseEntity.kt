package me.zhengjin.common.sso.entity

import me.zhengjin.common.core.entity.BaseEntity
import me.zhengjin.common.core.jpa.comment.annotation.JpaComment
import me.zhengjin.common.sso.utils.AuthUtils
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist

/**
 * 数据隔离基础实体
 */
@MappedSuperclass
abstract class IsolationBaseEntity : BaseEntity() {
    /**
     * 租户
     * 可按租户数据隔离
     */
    @JpaComment("租户id")
    @Column(name = "tenant_id", columnDefinition = "varchar(50) default 'system'")
    var tenantId: String? = null

    /**
     * 企业客户编码
     * 可按企业数据隔离
     */
    @JpaComment("企业客户名称")
    @Column(name = "enterprise_name", length = 200)
    var enterpriseName: String? = null

    @JpaComment("企业客户编码")
    @Column(name = "enterprise_code", columnDefinition = "varchar(50) default 'system'")
    var enterpriseCode: String? = null

    @PrePersist
    fun onPersist() {
        if (tenantId.isNullOrBlank()) {
            // 当前线程/请求是否包含经过验证的用户信息
            tenantId = if (AuthUtils.isLogin()) {
                AuthUtils.currentUser().tenantId
            } else {
                // 未找到经过验证的用户信息 可能是系统定时任务或异步操作 标记为系统操作
                // 如果不是系统操作 一定要!!!!!!!覆盖默认值!!!!!!!!
                "system"
            }
        }
        if (enterpriseName.isNullOrBlank()) {
            // 当前线程/请求是否包含经过验证的用户信息
            enterpriseName = if (AuthUtils.isLogin()) {
                AuthUtils.currentUser().enterpriseName
            } else {
                // 未找到经过验证的用户信息 可能是系统定时任务或异步操作 标记为系统操作
                // 如果不是系统操作 一定要!!!!!!!覆盖默认值!!!!!!!!
                "system"
            }
        }
        if (enterpriseCode.isNullOrBlank()) {
            // 当前线程/请求是否包含经过验证的用户信息
            enterpriseCode = if (AuthUtils.isLogin()) {
                AuthUtils.currentUser().enterpriseCode
            } else {
                // 未找到经过验证的用户信息 可能是系统定时任务或异步操作 标记为系统操作
                // 如果不是系统操作 一定要!!!!!!!覆盖默认值!!!!!!!!
                "system"
            }
        }
    }
}
