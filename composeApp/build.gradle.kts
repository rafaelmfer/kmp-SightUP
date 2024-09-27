import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
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
            baseName = "ComposeApp"
            isStatic = true
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
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.constraintlayout)
            implementation(libs.androidx.material)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.preview)
            implementation(compose.uiTooling)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            //
            implementation(libs.androidx.camera.view)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.vision.common)

            // CameraX
            implementation ("androidx.camera:camera-core:1.3.4")
            implementation (libs.androidx.camera.lifecycle.v110)
            implementation (libs.androidx.camera.view.v100alpha31)
            // ML Kit (para detección de rostros)
            implementation (libs.face.detection)
            // Otras dependencias relacionadas
            implementation (libs.androidx.camera.extensions)
            implementation (libs.androidx.core.ktx.v170)

            // Network
            implementation(libs.ktor.client.android)

            // Coroutines
            implementation(libs.kotlinx.coroutines.android)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Date & Time
            implementation(libs.kotlinx.datetime)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Network
            implementation(libs.ktorfit)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kotlinx.serialization.json)
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
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

