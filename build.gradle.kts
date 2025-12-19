plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
}

group = "com.github.leonidstein"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.0"
val allureVersion = "2.25.0"
val aspectJVersion = "1.9.24"
val restAssuredVersion = "5.5.6"
val commonsCodecVersion = "1.19.0"
val ownerVersion = "1.0.12"
val lombokVersion = "1.18.42"
val jacksonDataBindVersion = "2.20.0"
val assertJCoreVersion = "3.27.6"
val javaFakerVersion = "1.0.2"
val encodingStandard = "UTF-8"

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {

    agent("org.aspectj:aspectjweaver:${aspectJVersion}")

    // JUnit5
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Allure
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5")
    testImplementation("io.qameta.allure:allure-rest-assured")

    // RestAssured
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("commons-codec:commons-codec:$commonsCodecVersion")

    // Owner
    testImplementation("org.aeonbits.owner:owner:$ownerVersion")

    // Lombok
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // Jackson
    testImplementation("com.fasterxml.jackson.core:jackson-databind:$jacksonDataBindVersion")

    // AssertJ
    testImplementation("org.assertj:assertj-core:$assertJCoreVersion")

    // Fake data
    testImplementation("com.github.javafaker:javafaker:$javaFakerVersion")
}

tasks.withType<JavaCompile> {
    options.encoding = encodingStandard
}

tasks.test {
    useJUnitPlatform()

    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )

    reports {
        html.required.set(true)
        junitXml.required.set(true)
    }
}

tasks.register<Test>("apiTest") {
    description = "Запуск всех тестов (включая smoke и regression тестов)"

    useJUnitPlatform {
        includeTags("api")
    }
}

tasks.register<Test>("apiSmokeTest") {
    description = "Запуск smoke тестов"

    useJUnitPlatform {
        includeTags("api-smoke")
    }
}

tasks.register<Test>("apiRegressionTest") {
    description = "Запуск regression тестов"

    useJUnitPlatform {
        includeTags("api-regress")
    }
}