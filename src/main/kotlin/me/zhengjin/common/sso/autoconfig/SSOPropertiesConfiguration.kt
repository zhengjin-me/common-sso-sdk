package me.zhengjin.common.sso.autoconfig

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(SSOProperties::class)
@Configuration
class SSOPropertiesConfiguration
