# Android Architecture Hub

Android WebView wrapper for the original `Android Architecture Hub.html`.

## Build APK
Every push to `main` starts the **Build Android APK** GitHub Actions workflow. You can also start it manually from **Actions → Build Android APK → Run workflow**. A successful run uploads the debug APK as the `AndroidArchitectureHub-debug-apk` artifact (retained for 14 days).

- APK output: `app/build/outputs/apk/debug/app-debug.apk`
- Application ID: `com.aslmasiladam.androidarchitecturehub`
- Minimum Android: 6.0 (API 23)
- Compile/target SDK: 35
- Intended device: OPPO Reno5 / Android 13

The original HTML is bundled locally in the app. External CDN fonts, icons and libraries need an internet connection. The app requests internet access only; Android biometric/device-credential authentication is used for the app lock, and screenshots/recent-app previews are blocked.

The debug APK is for testing. For public production distribution, create a private release keystore and store its credentials as GitHub Actions secrets; never commit the keystore or passwords.
