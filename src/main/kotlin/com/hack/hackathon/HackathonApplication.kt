package com.hack.hackathon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory


@SpringBootApplication
class HackathonApplication

fun main(args: Array<String>) {
    runApplication<HackathonApplication>(*args)
}

@Configuration
class Config {
    @Bean
    fun restTemplate(): RestTemplate {
        val rest = RestTemplate()
        val defaultUriBuilderFactory = DefaultUriBuilderFactory()
        defaultUriBuilderFactory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY
        rest.uriTemplateHandler = defaultUriBuilderFactory
        return rest
    }
}

