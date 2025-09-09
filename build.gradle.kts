plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("application")
}

version = "0.1"
group = "com.example"

repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut:micronaut-inject-processor:4.6.3")
    kspTest("io.micronaut:micronaut-inject-processor:4.6.3")
    
    implementation("io.micronaut:micronaut-core:4.6.3")
    implementation("io.micronaut:micronaut-runtime:4.6.3")
    implementation("io.micronaut:micronaut-inject:4.6.3")
    implementation("io.micronaut:micronaut-context:4.6.3")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.25")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.25")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.12")
    
    testImplementation("io.micronaut.test:micronaut-test-junit5:4.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
}

allOpen {
    annotation("jakarta.inject.Singleton")
}

application {
    mainClass.set("com.example.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        javaParameters = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}