import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = "0.0.2"
group = "eu.jailbreaker.schematic"

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
    compileOnly("org.spigotmc:spigot:1.16.4")
    kapt("org.spigotmc:plugin-annotations:1.2.2-SNAPSHOT")
    compileOnly("org.spigotmc:plugin-annotations:1.2.2-SNAPSHOT")
}

fun ShadowJar.relocate(pattern: String) {
    this.relocate(pattern, "schematic.$pattern")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("Schematic.jar")

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