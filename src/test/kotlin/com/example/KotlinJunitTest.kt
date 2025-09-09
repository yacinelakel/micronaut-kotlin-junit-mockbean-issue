package com.example
import com.example.service.EchoServiceImpl
import com.example.service.EchoServiceInterface
import com.example.service.EchoServiceWithInterface
import io.micronaut.context.annotation.Replaces
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import org.mockito.Mockito

@MicronautTest
open class KotlinJunitTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Inject
    lateinit var echoService: EchoServiceImpl

    @Inject
    lateinit var echoServiceInterface: EchoServiceInterface

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

    @Test
    fun echoServiceIsMocked() {
        Assertions.assertTrue(Mockito.mockingDetails(echoService).isMock)
    }

    @Test
    fun echoServiceWithInterfaceIsMocked() {
        Assertions.assertTrue(Mockito.mockingDetails(echoServiceInterface).isMock)
    }

    @MockBean(EchoServiceImpl::class)
    open fun echoService(): EchoServiceImpl {
        return Mockito.mock(EchoServiceImpl::class.java)
    }

    @MockBean(EchoServiceWithInterface::class)
    @Replaces(EchoServiceInterface::class)
    open fun echoServiceWithInterface(): EchoServiceInterface {
        return Mockito.mock(EchoServiceWithInterface::class.java)
    }

}
