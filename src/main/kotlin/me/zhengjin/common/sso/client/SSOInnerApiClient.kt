package me.zhengjin.common.sso.client

import com.dtflys.forest.annotation.BaseRequest
import com.dtflys.forest.annotation.Body
import com.dtflys.forest.annotation.LogEnabled
import com.dtflys.forest.annotation.Post
import com.dtflys.forest.annotation.Var
import com.dtflys.forest.http.ForestResponse
import me.zhengjin.common.core.entity.HttpResult
import me.zhengjin.common.core.entity.PageResult
import me.zhengjin.common.dict.controller.vo.DictSearchVO
import me.zhengjin.common.dict.po.Dict
import me.zhengjin.common.sso.client.interceptor.SSOInnerApiInterceptor
import me.zhengjin.common.sso.client.vo.EnterpriseParamVO
import me.zhengjin.common.sso.client.vo.FieldCheckVO
import me.zhengjin.common.sso.client.vo.RoleSearchVO
import me.zhengjin.common.sso.client.vo.TenantSearchVO
import me.zhengjin.common.sso.client.vo.UserGrantRoleVO
import me.zhengjin.common.sso.client.vo.UserLoginVO
import me.zhengjin.common.sso.client.vo.UserSearchVO
import me.zhengjin.common.sso.dto.MenuAndResourceDTO
import me.zhengjin.common.sso.dto.RoleDTO
import me.zhengjin.common.sso.dto.RoleListDTO
import me.zhengjin.common.sso.dto.TenantDTO
import me.zhengjin.common.sso.dto.UserDetailDTO
import org.springframework.http.MediaType

@BaseRequest(
    baseURL = "\${ssoCenterApiBaseUrl}",
    timeout = 10000,
    retryCount = 3,
    contentType = MediaType.APPLICATION_JSON_UTF8_VALUE,
    interceptor = [SSOInnerApiInterceptor::class],
)
interface SSOInnerApiClient {
    /**
     * 用户名密码验证
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/user/password/check")
    fun checkUserPassword(@Body vo: UserLoginVO): ForestResponse<HttpResult<String>>

    /**
     * 查找用户
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/user/search")
    fun userSearch(@Body vo: UserSearchVO): ForestResponse<HttpResult<PageResult<UserDetailDTO>>>

    /**
     * 根据用户名查找用户
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/user/search/userNames")
    fun getUserByUserNames(@Body vo: UserSearchVO): HttpResult<List<UserDetailDTO>>

    /**
     * 查找角色
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/role/search")
    fun roleSearch(@Body vo: RoleSearchVO): ForestResponse<HttpResult<PageResult<RoleDTO>>>

    /**
     * 查找租户
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/tenant/search")
    fun tenantSearch(@Body vo: TenantSearchVO): ForestResponse<HttpResult<PageResult<TenantDTO>>>

    /**
     * 字典查询
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/dict/\${type}/list")
    fun dictSearch(
        @Body vo: DictSearchVO,
        @Var("type") type: String
    ): ForestResponse<HttpResult<PageResult<Dict.CodeName>>>

    /**
     * 获取当前用户的角色
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/user/\${userId}/roles")
    fun userRoleSearch(@Var("userId") userId: String): HttpResult<List<String>>

    /**
     * 用户追加角色
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/user/appendRole")
    fun appendRole(@Body vo: UserGrantRoleVO): HttpResult<String>

    /**
     * 创建角色
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/role/createEnterpriseRole")
    fun createEnterpriseRole(@Body vo: RoleDTO): HttpResult<String>

    /**
     * 注册企业
     * 参数是map
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/register")
    fun register(@Body map: MutableMap<String, Any>): HttpResult<MutableMap<String, Any>>

    /**
     * 获取当前用户的菜单及权限
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/user/\${userId}/menuAndResource")
    fun userMenuAndResourceSearch(@Var("userId") userId: String): HttpResult<MenuAndResourceDTO>

    /**
     * 获取当前用户的资源代码
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/user/\${userId}/resource")
    fun userResourceSearch(@Var("userId") userId: String): HttpResult<List<String>>

    /**
     * 获取当前用户的应用iconUrl
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/application/icon")
    fun getIconUrl(): HttpResult<String>

    /**
     * 用户字段校验 用户名, 邮箱, 手机号唯一性校验
     * @param id 新增或修改用户时传入 可以为空
     * @param checkType 检查类型 userName, email, mobile
     * @param data 要校验的值
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/columnValidCheck")
    fun columnValidCheck(@Body data: FieldCheckVO): HttpResult<String>

    /**
     * 根据企业code获取自定义参数
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/enterprise/\${enterpriseCode}")
    fun entParameter(@Var("enterpriseCode") enterpriseCode: String): HttpResult<String>

    /**
     * 获取角色列表
     * 根据应用     和角色中的扩展参数来获取角色列表
     * 根据流程角色
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/getRoleList")
    fun getRoleList(@Body data: RoleSearchVO): HttpResult<List<RoleListDTO>>

    /**
     * 设置企业自定义参数
     */
    @LogEnabled(logResponseContent = true)
    @Post("/innerApi/setEntParameter")
    fun setEntParameter(@Body enterpriseParamVO: EnterpriseParamVO): HttpResult<String>
}
