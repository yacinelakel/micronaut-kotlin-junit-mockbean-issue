package com.example

import io.micronaut.context.ApplicationContext
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

    /**
     * This test demonstrates the issue: @MockBean should inject a mocked bean,
     * but it doesn't work properly with Kotlin and JUnit in Micronaut.
     * 
     * Expected: The injected echoService should be a mock
     * Actual: Test fails with TestInstantiationException due to annotation processing issues
     */
    @Test
    fun echoServiceIsMocked() {
        Assertions.assertTrue(Mockito.mockingDetails(echoService).isMock, 
            "echoService should be a mock when using @MockBean")
    }

    /**
     * This test shows that basic Micronaut context creation works
     * when not using @MicronautTest annotation
     */
    @Test 
    fun contextLoadsManually() {
        val ctx = ApplicationContext.run()
        Assertions.assertTrue(ctx.isRunning)
        
        // Get the real implementation (not mocked)
        val realEchoService = ctx.getBean(EchoService::class.java)
        Assertions.assertFalse(Mockito.mockingDetails(realEchoService).isMock,
            "Real service should not be a mock")
        
        // Test the real implementation
        val result = realEchoService.echo("test")
        Assertions.assertEquals("Echo: test", result)
        
        ctx.close()
    }

    @MockBean(EchoServiceImpl::class)
    fun echoService(): EchoService {
        return Mockito.mock(EchoService::class.java)
    }
}