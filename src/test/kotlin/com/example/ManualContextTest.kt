package com.example

import io.micronaut.context.ApplicationContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

/**
 * This test class demonstrates that Micronaut works fine without @MicronautTest
 * and shows the difference between the real service and what should happen with mocks.
 */
class ManualContextTest {

    @Test
    fun realServiceWorks() {
        val ctx = ApplicationContext.run()
        
        val realEchoService = ctx.getBean(EchoService::class.java)
        
        // Verify it's not a mock
        Assertions.assertFalse(Mockito.mockingDetails(realEchoService).isMock,
            "Real service should not be a mock")
        
        // Test the real implementation
        val result = realEchoService.echo("test")
        Assertions.assertEquals("Echo: test", result)
        
        ctx.close()
    }

    @Test
    fun manualMockWorks() {
        // Create a manual mock
        val mockService = Mockito.mock(EchoService::class.java)
        
        // Configure the mock
        Mockito.`when`(mockService.echo("test")).thenReturn("Mocked: test")
        
        // Verify it's a mock
        Assertions.assertTrue(Mockito.mockingDetails(mockService).isMock,
            "Manual mock should be detected as a mock")
        
        // Test the mock
        val result = mockService.echo("test")
        Assertions.assertEquals("Mocked: test", result)
    }
}