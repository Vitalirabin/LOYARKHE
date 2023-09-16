package com.kheloyar.livegame.presentation.utils

import android.annotation.TargetApi
import android.os.Build
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

open class KheloyarWebClient : WebViewClient() {
    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(
        viewKheloyar: WebView,
        requestKheloyar: WebResourceRequest
    ): Boolean {
        if (viewKheloyar.url != "")
            viewKheloyar.loadUrl(requestKheloyar.url.toString())
        else viewKheloyar.loadUrl(requestKheloyar.url.toString())
        if (requestKheloyar.url.toString() != "")
            viewKheloyar.loadUrl(requestKheloyar.url.toString())
        else viewKheloyar.loadUrl(requestKheloyar.url.toString())
        return true
    }

    // Для старых устройств
    @Deprecated("Deprecated in Java")
    override fun shouldOverrideUrlLoading(viewKheloyar: WebView, urlKheloyar: String): Boolean {
        if (viewKheloyar.url != "")
            viewKheloyar.loadUrl(urlKheloyar)
        else viewKheloyar.loadUrl(urlKheloyar)
        if (urlKheloyar.length > 1)
            viewKheloyar.loadUrl(urlKheloyar)
        else viewKheloyar.loadUrl(urlKheloyar)
        return true
    }

    override fun onPageFinished(viewKheloyar: WebView?, urlKheloyar: String?) {
        if (viewKheloyar != null) CookieManager.getInstance().flush()
        else if (urlKheloyar == null)
            super.onPageFinished(viewKheloyar, urlKheloyar)
        else if (urlKheloyar.contains("")) super.onPageFinished(viewKheloyar, urlKheloyar)
        super.onPageFinished(viewKheloyar, urlKheloyar)
    }
}