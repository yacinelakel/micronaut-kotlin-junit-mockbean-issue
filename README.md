# Micronaut Kotlin JUnit MockBean Issue

This repository demonstrates an issue with Micronaut's `@MockBean` annotation when using Kotlin and JUnit 5.

## Issue Description

When using Micronaut with Kotlin and JUnit 5, the `@MockBean` annotation fails to properly inject mocked beans into test classes. This is due to annotation processing issues between Micronaut, Kotlin, and the testing framework.

## Project Setup

- **Micronaut Version**: 4.6.3
- **Kotlin Version**: 1.9.25
- **JUnit Version**: 5.11.3
- **Mockito Version**: 5.14.2
- **JDK**: 17
- **Build Tool**: Gradle with Kotlin DSL

## Structure

- `src/main/kotlin/com/example/EchoService.kt` - Simple service interface
- `src/main/kotlin/com/example/EchoServiceImpl.kt` - Singleton implementation 
- `src/test/kotlin/com/example/KotlinJunitTest.kt` - Test demonstrating the @MockBean issue
- `src/test/kotlin/com/example/ManualContextTest.kt` - Test showing manual mocking works

## The Problem

### What Should Work (but doesn't):

```kotlin
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
```

**Expected**: The `@MockBean` annotation should replace the real `EchoServiceImpl` with a mock.
**Actual**: Test fails with `TestInstantiationException` due to annotation processing issues.

### What Works:

```kotlin
@Test
fun manualMockWorks() {
    val mockService = Mockito.mock(EchoService::class.java)
    Mockito.`when`(mockService.echo("test")).thenReturn("Mocked: test")
    
    Assertions.assertTrue(Mockito.mockingDetails(mockService).isMock)
    val result = mockService.echo("test")
    Assertions.assertEquals("Mocked: test", result)
}
```

## Running the Tests

### To see the issue:
```bash
gradle test --tests "*KotlinJunitTest*"
```
This will fail with `TestInstantiationException` showing the @MockBean issue.

### To see what works:
```bash
gradle test --tests "*manualMockWorks*"
```
This passes, showing that manual mocking works fine.

## Root Cause

The issue stems from Micronaut's annotation processing not working properly with Kotlin compilation. The `@MicronautTest` and `@MockBean` annotations require compile-time processing to generate the necessary bean definitions, but this fails in the Kotlin compilation pipeline.

## Workarounds

1. **Manual Mocking**: Create mocks manually without `@MockBean`
2. **Java Tests**: Use Java for test classes that need `@MockBean`
3. **Manual Context**: Use `ApplicationContext.run()` manually and replace beans programmatically

## Expected Resolution

This issue should be resolved by:
1. Proper annotation processor configuration for Kotlin
2. Using KSP (Kotlin Symbol Processing) instead of KAPT
3. Micronaut providing better Kotlin support for testing annotations
