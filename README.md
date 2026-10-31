# Android Architecture Hub

Android WebView wrapper for the original `Android Architecture Hub.html`.

## Build APK
Push to `main` or open **Actions → Build Android APK → Run workflow**. The workflow builds a debug APK and uploads it as the `AndroidArchitectureHub-debug-apk` artifact.

- Application ID: `com.aslmasiladam.androidarchitecturehub`
- Minimum Android: 6.0 (API 23)
- Compile/target SDK: 35
- Intended device: OPPO Reno5 / Android 13

The original HTML is bundled locally in the app. External CDN fonts, icons and libraries need an internet connection.

The debug APK is for testing. For public production distribution, create a private release keystore and store its credentials as GitHub Actions secrets; never commit the keystore or passwords.
