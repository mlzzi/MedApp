
plugins {
    id("com.android.application") version "8.0.1" apply false
    id("com.android.library") version "8.0.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.0"
    id("com.google.gms.google-services") version "4.3.15" apply false
}

buildscript {
    val hiltVersion: String by rootProject

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("com.android.tools.build:gradle:8.2.0")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}