package com.example

import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

@MicronautTest
class KotlinJunitTest {

    @Inject
    lateinit var echoService: EchoService

    @Test
    fun echoServiceIsMocked() {
        Assertions.assertTrue(Mockito.mockingDetails(echoService).isMock)
    }

    @MockBean(EchoServiceImpl::class)
    fun echoService(): EchoService {
        return Mockito.mock(EchoService::class.java)
    }
}