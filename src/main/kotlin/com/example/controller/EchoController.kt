package com.example.controller

import com.example.service.EchoServiceImpl
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class EchoController(private val echoService: EchoServiceImpl) {

    @Get("/echo/{message}")
    fun echo(message: String): String {
        return echoService.echo(message)
    }
}