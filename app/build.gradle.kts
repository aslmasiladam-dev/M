plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.aslmasiladam.androidarchitecturehub"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.aslmasiladam.androidarchitecturehub"
        minSdk = 23
        targetSdk = 35
        versionCode = 2
        versionName = "1.1.0"
    }
}

dependencies {
    implementation("androidx.biometric:biometric:1.1.0")
}
