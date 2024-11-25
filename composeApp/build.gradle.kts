import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.gms)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.swiftKlib)
}

swiftklib {
    create("native"){
        path = file("../iosApp/iosApp/native")
        packageName = "com.europa.sightup.native"
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.europa.sightup")
            baseName = "ComposeApp"
            isStatic = true
            export(libs.kmpnotifier)
        }
        iosTarget.compilations {
            val main by getting {
                cinterops {
                    create("native")
                }
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.core.splashscreen)

            // Design
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.constraintlayout)
            implementation(libs.androidx.material)
            implementation(compose.uiTooling)
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Koin
            implementation(libs.koin.android)

            // Network
            implementation(libs.ktor.client.android)

            // Coroutines
            implementation(libs.kotlinx.coroutines.android)

            // Navigation CMP
            implementation(libs.navigation.compose)

            // Camera X
            implementation (libs.androidx.camera.core)
            implementation (libs.androidx.camera.camera2)
            implementation (libs.androidx.camera.lifecycle)
            implementation (libs.androidx.camera.view)

            // ML Kit - Face detector
            implementation (libs.face.detection)

            // ExoPlayer
            implementation(libs.androidx.media3.exoplayer)
            implementation(libs.androidx.media3.exoplayer.dash)
            implementation(libs.androidx.media3.ui)

            // Wearable
            implementation(libs.play.services.wearable)
        }

        commonMain.dependencies {
            // Design
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.animationGraphics)
            implementation(compose.animation)
            implementation(libs.constraintlayout.compose.multiplatform)

            // CMP Toast
            implementation(libs.cmptoast)

            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Date & Time
            implementation(libs.kotlinx.datetime)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Network
            implementation(libs.ktorfit)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)

            // KVault - Encrypted Cache Storage
            implementation(libs.kvault)

            // Notifications
            api(libs.kmpnotifier)
            implementation(libs.kmpnotifier.benasher44uuid)

            // Navigation CMP
            implementation(libs.navigation.compose)

            // Coil - Image loader
            implementation(libs.landscapist.coil3)

            // Compottie - Lottie animation
            implementation(libs.compottie)
            implementation(libs.compottie.dot)
            implementation(libs.compottie.network)
            implementation(libs.compottie.resources)

            implementation(libs.kmpauth.google) //Google One Tap Sign-In
            implementation(libs.kmpauth.firebase) //Integrated Authentications with Firebase
            implementation(libs.kmpauth.uihelper) //UiHelper SignIn buttons (AppleSignIn, GoogleSignInButton)

            // Helper to Asks Permissions - Moko Permissions
            api(libs.permissions)

            // Media Player
            implementation(libs.compose.multiplatform.media.player)
        }

        iosMain.dependencies {
            // Network
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.europa.sightup"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.europa.sightup"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}

buildConfig {
    packageName("com.europa.sightup")
    forClass("BuildConfigKMP") {
        buildConfigField("APP_NAME", "SightUP")
        buildConfigField("APP_VERSION", project.android.defaultConfig.versionName)
        buildConfigField("WEB_CLIENT_ID", "456640147314-nuhont2kmvg9mqahlem851hkti0rjvs0.apps.googleusercontent.com")
        buildConfigField("BASE_URL_BACKEND_ANDROID_EMU", localProperties.getProperty("BASE_URL_BACKEND_ANDROID_EMU"))
        buildConfigField("BASE_URL_BACKEND_IOS_EMU", localProperties.getProperty("BASE_URL_BACKEND_IOS_EMU"))
    }
}