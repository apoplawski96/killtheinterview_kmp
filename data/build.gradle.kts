import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    id("com.android.library")
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

//    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }

    js {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "DataLayer"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(compose.foundation)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.voyager.koin)
            implementation(libs.composeImageLoader)
            implementation(libs.napier)
            implementation(libs.moko.mvvm)
            implementation(libs.ktor.core)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatformSettings)
            implementation(libs.koin.core)
            implementation(libs.kstore)
            implementation("co.touchlab:stately-common:2.0.5")
            implementation(libs.sqlDelight.coroutines)
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activityCompose)
            implementation(libs.compose.uitooling)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.android)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.sqlDelight.driver.js)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqlDelight.driver.native)
        }
    }
}

compose.experimental {
    web.application {}
}

android {
    namespace = "sectonone.droidsoft.ap.data"
    compileSdk = 34
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

sqldelight {
    databases {
        create("KTIDatabase2") {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("sectonone.droidsoft.ap.data.db")
        }
    }
}


