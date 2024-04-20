package com.hack.hackathon

import com.vaadin.flow.theme.Theme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class HackathonApplication

fun main(args: Array<String>) {
    runApplication<HackathonApplication>(*args)
}

@Configuration
class Config {
    @Bean
    fun restTemplate(): RestTemplate =
        RestTemplate()
}

