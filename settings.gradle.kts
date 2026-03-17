pluginManagement {
    repositories {
        // Repositories
        maven("https://maven.deftu.dev/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://jitpack.io/")

        // Snapshots
        maven("https://maven.deftu.dev/snapshots")
        maven("https://s01.oss.sonatype.org/content/groups/public/")
        mavenLocal()

        // Default repositories
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version("2.3.0")
        id("dev.deftu.gradle.multiversion-root") version("2.70.0")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.8.+")
}

rootProject.name = "OmniCore"
rootProject.buildFileName = "root.gradle.kts"

listOf(
    "1.21.1-neoforge",
    "1.21.1-fabric",

    "1.21.4-neoforge",
    "1.21.4-fabric",

    "1.21.5-neoforge",
    "1.21.5-fabric",

    "1.21.8-neoforge",
    "1.21.8-fabric",

    "1.21.10-neoforge",
    "1.21.10-fabric",

    "1.21.11-neoforge",
    "1.21.11-fabric",

    "26.1-fabric",
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version").apply {
            if (!exists() && !mkdirs()) {
                throw IllegalStateException("Could not create project directory: $this")
            }
        }

        buildFileName = "../../build.gradle.kts"
    }
}
