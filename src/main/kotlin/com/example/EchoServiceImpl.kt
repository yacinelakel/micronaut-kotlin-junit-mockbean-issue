package com.example

import jakarta.inject.Singleton

@Singleton
class EchoServiceImpl : EchoService {
    override fun echo(message: String): String {
        return "Echo: $message"
    }
}