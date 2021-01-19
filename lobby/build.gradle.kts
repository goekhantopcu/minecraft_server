import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = "0.0.2"
group = "eu.jailbreaker.lobby"

plugins {
    id("distribution")
    id("net.linguica.maven-settings") version "0.5"
    kotlin("plugin.serialization") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly(project(":chat"))
    compileOnly("org.spigotmc:spigot:1.16.4")
    compileOnly("ru.tehkode:PermissionsEx:1.23")
    kapt("org.spigotmc:plugin-annotations:1.2.2-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.5.0")
    compileOnly("org.spigotmc:plugin-annotations:1.2.2-SNAPSHOT")
    compileOnly("com.comphenix.packetwrapper:PacketWrapper:1.13-R0.1-20180825.172109-2")
}

fun ShadowJar.relocate(pattern: String) {
    this.relocate(pattern, "lobby.$pattern")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("Lobby.jar")

        relocate("kotlin")
        relocate("org.intellij.lang.annotations")
        relocate("org.jetbrains.annotations")
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