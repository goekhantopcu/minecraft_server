import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = "0.0.1"
group = "eu.jailbreaker.telegram"

plugins {
    id("distribution")
    id("net.linguica.maven-settings") version "0.5"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-cio:1.5.0")
    implementation("io.ktor:ktor-client-gson:1.5.0") {
        exclude("com.google.gson")
    }
    implementation("io.ktor:ktor-client-serialization:1.5.0")
}

fun ShadowJar.relocate(pattern: String) {
    this.relocate(pattern, "telegram.$pattern")
}

tasks {
    named<ShadowJar>("shadowJar") {
        relocate("io.ktor")
        relocate("it.unimi.dsi")

        relocate("org.slf4j")
        relocate("org.jose4j")
        relocate("org.aopalliance")
        relocate("org.intellij.lang")
        relocate("org.yaml.snakeyaml")
        relocate("org.checkerframework")
        relocate("org.apache.commons.io")
        relocate("org.apache.logging.log4j")
        relocate("org.apache.commons.lang3")
        relocate("org.apache.commons.codec")
        relocate("org.jetbrains.annotations")

        relocate("com.google")
        relocate("com.mojang.util")
        relocate("com.fasterxml.jackson")
        relocate("com.github.ben-manes.caffeine")
    }

    build {
        dependsOn(shadowJar)
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}