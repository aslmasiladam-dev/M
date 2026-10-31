package com.aslmasiladam.androidarchitecturehub

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {
    private lateinit var webView: WebView

    @Suppress("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(8, 22, 34)
        window.navigationBarColor = Color.rgb(8, 22, 34)
        window.decorView.systemUiVisibility = 0

        webView = WebView(this).apply {
            setBackgroundColor(Color.rgb(8, 22, 34))
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.loadsImagesAutomatically = true
            settings.javaScriptCanOpenWindowsAutomatically = false
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    view.evaluateJavascript(
                        """
                        (function() {
                          if (document.getElementById('aah-mobile-polish')) return;
                          const style = document.createElement('style');
                          style.id = 'aah-mobile-polish';
                          style.textContent = `
                            * { -webkit-tap-highlight-color: transparent; }
                            html { scroll-behavior: smooth; }
                            body {
                              background: radial-gradient(ellipse at 50% -15%, #123b42 0%, #081622 48%) !important;
                              color: #e7eef5;
                              padding-bottom: env(safe-area-inset-bottom);
                            }
                            header {
                              background: rgba(8,22,34,.94) !important;
                              border-bottom-color: rgba(61,220,132,.18) !important;
                              box-shadow: 0 8px 30px rgba(0,0,0,.18);
                            }
                            header > div { min-height: 68px; }
                            header h1 { letter-spacing: -.035em; }
                            main { padding-top: 22px !important; }
                            main > section { scroll-margin-top: 88px; }
                            .glow-green { box-shadow: 0 18px 55px rgba(0,0,0,.22), 0 0 35px rgba(61,220,132,.08); }
                            .stack-card, main .bg-android-card {
                              box-shadow: 0 10px 28px rgba(0,0,0,.14);
                            }
                            button, a, input { transition: all .18s ease; }
                            button:focus-visible, a:focus-visible, input:focus-visible {
                              outline: 2px solid #3ddc84; outline-offset: 3px;
                            }
                            pre[class*="language-"] {
                              max-width: 100%; overflow-x: auto;
                              -webkit-overflow-scrolling: touch;
                            }
                            #toast { right: 16px; bottom: calc(18px + env(safe-area-inset-bottom)); }
                            @media (max-width: 640px) {
                              header > div {
                                height: auto !important; min-height: 66px;
                                padding: 10px 14px !important; gap: 10px;
                              }
                              header h1 { font-size: 14px !important; line-height: 1.25; }
                              header p { font-size: 10px !important; }
                              header .w-10 { width: 36px !important; height: 36px !important; flex: 0 0 36px; }
                              header a[href="#quickstart"] {
                                padding: 9px 10px !important; font-size: 11px !important;
                                white-space: nowrap;
                              }
                              main {
                                padding: 18px 14px 34px !important;
                                gap: 34px !important;
                              }
                              main > section { margin-bottom: 34px; }
                              main > section:first-child {
                                border-radius: 22px !important;
                                padding: 25px 20px !important;
                              }
                              main > section:first-child h2 {
                                font-size: clamp(28px, 8vw, 38px) !important;
                                line-height: 1.12 !important; letter-spacing: -.045em;
                              }
                              main > section:first-child p { font-size: 14px !important; line-height: 1.7; }
                              main > section:first-child .text-\[260px\] { font-size: 150px !important; }
                              main h3 { font-size: 21px !important; line-height: 1.25; }
                              main .grid { gap: 14px !important; }
                              main .p-6 { padding: 17px !important; }
                              main .p-5 { padding: 16px !important; }
                              main .rounded-xl { border-radius: 17px !important; }
                              main .text-sm { line-height: 1.65; }
                              main .text-xs { line-height: 1.55; }
                              #globalSearch { width: 100%; }
                              footer { padding: 24px 14px !important; line-height: 1.6; }
                              pre[class*="language-"] { border-radius: 13px; }
                              code[class*="language-"] { font-size: 12px !important; }
                            }
                          `;
                          document.head.appendChild(style);
                        })();
                        """.trimIndent(),
                        null
                    )
                }
            }
            webChromeClient = WebChromeClient()
        }

        setContentView(webView)
        if (savedInstanceState == null) {
            webView.loadUrl("file:///android_asset/index.html")
        } else {
            webView.restoreState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }

    override fun onDestroy() {
        if (::webView.isInitialized) webView.destroy()
        super.onDestroy()
    }
}
