package com.hack.hackathon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class HackathonApplication

fun main(args: Array<String>) {
    runApplication<HackathonApplication>(*args)
}

@Bean
fun restTemplate() : RestTemplate =
    RestTemplate()
