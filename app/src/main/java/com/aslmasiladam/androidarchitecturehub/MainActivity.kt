package com.aslmasiladam.androidarchitecturehub

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.fragment.app.FragmentActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat

class MainActivity : FragmentActivity() {
    private var webView: WebView? = null
    private var authenticated = false
    private var needsAuthentication = true
    private var authenticating = false

    private val allowedAuthenticators =
        BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.DEVICE_CREDENTIAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep app content out of screenshots and the recent-apps preview.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        window.statusBarColor = Color.rgb(8, 22, 34)
        window.navigationBarColor = Color.rgb(8, 22, 34)

        showLockedScreen()
    }

    override fun onStart() {
        super.onStart()
        if (needsAuthentication && !authenticating) {
            authenticateUser()
        }
    }

    override fun onStop() {
        // Require authentication again after the app leaves the foreground.
        if (authenticated && !authenticating) {
            needsAuthentication = true
        }
        super.onStop()
    }

    private fun showLockedScreen() {
        setContentView(TextView(this).apply {
            text = "Reno5 Ultimate Hub\n\nجارٍ التحقق من هويتك..."
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = android.view.Gravity.CENTER
            setBackgroundColor(Color.rgb(8, 22, 34))
        })
    }

    private fun authenticateUser() {
        val manager = BiometricManager.from(this)
        val availability = manager.canAuthenticate(allowedAuthenticators)
        if (availability != BiometricManager.BIOMETRIC_SUCCESS) {
            Toast.makeText(
                this,
                "فعّل قفل شاشة آمنًا (PIN أو نمط أو كلمة مرور) أو سجّل بصمة أولًا.",
                Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }

        authenticating = true
        showLockedScreen()

        val prompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    authenticating = false
                    authenticated = true
                    needsAuthentication = false
                    showHub()
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    authenticating = false
                    Toast.makeText(
                        this@MainActivity,
                        "لم يتم فتح التطبيق: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        this@MainActivity,
                        "لم يتم التعرف على البصمة. حاول مرة أخرى أو استخدم PIN الجهاز.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("حماية Reno5 Ultimate Hub")
            .setSubtitle("أكّد هويتك بالبصمة أو بقفل شاشة الهاتف")
            .setAllowedAuthenticators(allowedAuthenticators)
            .setConfirmationRequired(false)
            .build()

        prompt.authenticate(promptInfo)
    }

    @Suppress("SetJavaScriptEnabled")
    private fun showHub() {
        val page = WebView(this).apply {
            setBackgroundColor(Color.rgb(8, 22, 34))
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = false
            settings.allowContentAccess = false
            settings.loadsImagesAutomatically = true
            settings.javaScriptCanOpenWindowsAutomatically = false
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: android.webkit.WebResourceRequest
                ): Boolean {
                    val target = request.url
                    return !(target.scheme == "file" &&
                        target.host == null &&
                        target.path?.startsWith("/android_asset/") == true)
                }
            }
            webChromeClient = WebChromeClient()
        }
        webView = page
        setContentView(page)
        page.loadUrl("file:///android_asset/index.html")
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        val page = webView
        if (page != null && page.canGoBack()) {
            page.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        webView?.apply {
            stopLoading()
            loadUrl("about:blank")
            removeAllViews()
            destroy()
        }
        webView = null
        super.onDestroy()
    }
}
