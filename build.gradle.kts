import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.21" apply false
    kotlin("kapt") version "1.4.21" apply false
}

version = "0.0.1"
group = "eu.jailbreaker"

allprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
    }
    repositories {
        mavenLocal()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")

        maven(url = "https://jitpack.io/")
        maven(url = "https://repo.minebench.de/")
        maven(url = "https://libraries.minecraft.net/")
        maven(url = "https://jailbreaker.eu/repositories/")
        maven(url = "https://maven.elmakers.com/repository/")
        maven(url = "https://repo.caseif.net/content/groups/public/")
        maven(url = "https://repo.dmulloy2.net/nexus/repository/public/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}