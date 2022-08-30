package me.zhengjin.common.sso.service

import me.zhengjin.common.dict.adapter.DictAdapter
import me.zhengjin.common.dict.controller.vo.DictSearchVO
import me.zhengjin.common.dict.po.Dict
import me.zhengjin.common.sso.client.SSOInnerApiClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component

@ConditionalOnExpression("'center'.equalsIgnoreCase('\${customize.sso.model:center}')")
@Component
class SSOEnterpriseDictAdapter(
    private val ssoApiClient: SSOInnerApiClient
) : DictAdapter {
    companion object {
        val regex = Regex("^sso_enterprise_([_\\-\\w]+)\$")
    }

    override fun handler(dictSearchVO: DictSearchVO): Page<Dict.CodeName> {
        val response = ssoApiClient.dictSearch(dictSearchVO, dictSearchVO.type!!)
        val body = response.result.body
        if (response.isSuccess && response.result.isOk && body?.content?.isNotEmpty() == true) {
            return PageImpl(body.content!!, dictSearchVO.getPageable(), body.totalElements!!)
        }
        return PageImpl(emptyList<Dict.CodeName>())
    }

    override fun support(type: String): Boolean {
        return regex.matches(type)
    }
}
