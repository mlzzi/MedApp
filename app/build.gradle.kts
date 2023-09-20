import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

apply(from = "../config/detekt/detekt.gradle")

// Getting keystore properties
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.leafwise.medapp"
    compileSdk = 34

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    defaultConfig {
        applicationId = "com.leafwise.medapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
        create("staging") {
            initWith(getByName("debug"))
            applicationIdSuffix = ".staging"
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-rules-staging.pro"
            )
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = LibVersion.composeCompilerVersion
    }
    packaging {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

object LibVersion {
    const val composeVersion = "2023.05.01"
    const val composeCompilerVersion = "1.4.7"
    const val navigationCompose = "2.5.3"
    const val roomVersion = "2.5.1"
    const val retrofitVersion = "2.9.0"
    const val moshiVersion = "1.14.0"
    const val coilVersion = "2.3.0"
    const val flowerVersion = "3.1.0"
}

dependencies {
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    val composeBom = platform("androidx.compose:compose-bom:${LibVersion.composeVersion}")

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3:1.2.0-alpha03")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.navigation:navigation-compose:${LibVersion.navigationCompose}")

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hiltVersion"]}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra["hiltVersion"]}")

    implementation("androidx.room:room-runtime:${LibVersion.roomVersion}")
    implementation("androidx.room:room-ktx:${LibVersion.roomVersion}")
    implementation("androidx.room:room-testing:${LibVersion.roomVersion}")
    ksp("androidx.room:room-compiler:${LibVersion.roomVersion}")

    implementation("io.github.hadiyarajesh.flower-retrofit:flower-retrofit:${LibVersion.flowerVersion}") {
        because("Flower simplifies networking and database caching on Android/Multiplatform")
    }

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // UI Tests
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Android Studio Preview support
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:${LibVersion.retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${LibVersion.retrofitVersion}")

    //Permissions Compose
    implementation("com.google.accompanist:accompanist-permissions:0.12.0")

    //Gson
    implementation("com.google.code.gson:gson:${LibVersion.retrofitVersion}")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")
}

// Pass options to Room ksp processor
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}

// Make Kapt-generated stubs to target JDK 17
tasks.withType<org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask>().configureEach {
    kotlinOptions.jvmTarget = "17"
}
