package com.example.service

import jakarta.inject.Singleton

@Singleton
class EchoServiceWithInterface : EchoServiceInterface {
    override fun echo(message: String): String {
        return message
    }
}
