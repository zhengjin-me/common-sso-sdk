package me.zhengjin.common.sso.service

import me.zhengjin.common.dict.adapter.DictAdapter
import me.zhengjin.common.dict.controller.vo.DictSearchVO
import me.zhengjin.common.dict.po.Dict
import me.zhengjin.common.sso.client.SSOInnerApiClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component

/**
 * @date 2021/11/26 11:37
 * @version 1.00
 */
@ConditionalOnExpression("'center'.equalsIgnoreCase('\${customize.sso.model:center}')")
@Component
class SSORoleDictAdapter(
    private val ssoApiClient: SSOInnerApiClient
) : DictAdapter {
    companion object {
        val regex = Regex("^sso_role_([_\\-\\w,]+)\$")
        const val prefix = "sso_"
    }

    /**
     * @param dictSearchVO 字典查询时的传入内容, 实现类应尽可能只使用searchData作为查询项目, 字典分页时, 通过getPageable获取分页参数
     *
     * 字典查询
     */
    override fun handler(dictSearchVO: DictSearchVO): Page<Dict.CodeName> {
        dictSearchVO.type = dictSearchVO.type?.removePrefix(prefix)
        val response = ssoApiClient.dictSearch(dictSearchVO, dictSearchVO.type!!)
        if (response.isSuccess && response.result.isOk && response.result.body?.content?.isNotEmpty() == true) {
            return PageImpl(response.result.body!!.content!!, dictSearchVO.getPageable(), response.result.body!!.totalElements!!)
        }
        return PageImpl(emptyList<Dict.CodeName>())
    }

    /**
     * @param type 字典类型, 如果支持此类型查询则返回true
     *
     * 是否支持对传入的字典类型进行查询
     */
    override fun support(type: String): Boolean {
        return regex.matches(type)
    }
}
