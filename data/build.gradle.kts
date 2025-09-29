import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

apply(from = "${rootProject.projectDir}/flavors.gradle")
android {
    namespace = "com.prado.eduardo.luiz.newsapp.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        /**
         * this is not a good practice to expose api key in the code
         * this is only for the reviewer doesnt need to create api key to test the app
         * in a real world app you should use a safer way to store your api key
         * like using a backend server or a secure vault
         * or at least use NDK to store your api key in a native library
         */
        val apiKey: String =
            gradleLocalProperties(rootDir, providers)
                .getProperty("NEWS_API_KEY") ?: "38ea4eb3ca424da08552d9f13ae8d12f"

        buildConfigField(
            "String",
            "NEWS_API_KEY",
            "\"$apiKey\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshiconverter)
    implementation(libs.moshi)

    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.koin.android)
    implementation(libs.koin.core)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
