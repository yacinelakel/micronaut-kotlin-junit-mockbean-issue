package com.example.service

import jakarta.inject.Singleton

@Singleton
open class EchoServiceImpl {
    open fun echo(message: String): String {
        return message
    }
}