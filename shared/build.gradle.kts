plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("app.cash.sqldelight") version "2.0.2"

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.2")
           // implementation(compose.material)
            api("dev.icerock.moko:mvvm-core:0.16.1") // only ViewModel, EventsDispatcher, Dispatchers.UI
            api("dev.icerock.moko:mvvm-compose:0.16.1") // api mvvm-core, getViewModel for Compose Multi platfrom
            implementation("androidx.compose.runtime:runtime:1.5.0")
            implementation(libs.androidx.ui.android)
            implementation(libs.androidx.material3.android)
        }

        sourceSets.androidMain.dependencies {

            implementation("app.cash.sqldelight:android-driver:2.0.2")
            api("androidx.core:core-ktx:1.12.0")
            api("androidx.activity:activity-compose:1.8.2")
            api("androidx.appcompat:appcompat:1.6.1")
        }

        // or iosMain, windowsMain, etc.
        sourceSets.nativeMain.dependencies {
            implementation("app.cash.sqldelight:native-driver:2.0.2")
        }


        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.contactssample"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}




sqldelight {
    databases {
        create("MyAppSQLDelightDatabase") {
            packageName.set("com.example.contactssample.db")
            generateAsync.set(true)
        }
    }
}
