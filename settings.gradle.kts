pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    val kotlinVersion: String by settings
    val detektVersion: String by settings
    plugins {
        id("org.jetbrains.kotlin.android") version kotlinVersion apply false
//        id("io.gitlab.arturbosch.detekt") version detektVersion
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MedApp"
include(":app")
