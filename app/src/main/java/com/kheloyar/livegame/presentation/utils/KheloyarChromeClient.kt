package com.kheloyar.livegame.presentation.utils

import android.net.Uri
import android.webkit.JsResult
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView

open class KheloyarChromeClient : WebChromeClient() {

    override fun onJsAlert(
        viewKheloyar: WebView?,
        urlKheloyar: String?,
        messageKheloyar: String?,
        resultKheloyar: JsResult?
    ): Boolean {
        return if (viewKheloyar != null)
            if (urlKheloyar != null)
                if (messageKheloyar != null)
                    if (resultKheloyar != null)
                        super.onJsAlert(viewKheloyar, urlKheloyar, messageKheloyar, resultKheloyar)
                    else super.onJsAlert(viewKheloyar, urlKheloyar, messageKheloyar, null)
                else super.onJsAlert(viewKheloyar, urlKheloyar, null, null)
            else super.onJsAlert(viewKheloyar, null, null, null)
        else super.onJsAlert(null, null, null, null)
    }

    override fun onShowFileChooser(
        viewKheloyar: WebView?,
        filePathCallbackKheloyar: ValueCallback<Array<Uri>>?,
        fileChooserParamsKheloyar: FileChooserParams?
    ): Boolean {
        return if (viewKheloyar != null)
            if (fileChooserParamsKheloyar != null)
                if (filePathCallbackKheloyar != null)
                    super.onShowFileChooser(viewKheloyar, filePathCallbackKheloyar, fileChooserParamsKheloyar)
                else super.onShowFileChooser(viewKheloyar, null, fileChooserParamsKheloyar)
            else super.onShowFileChooser(viewKheloyar, null, null)
        else super.onShowFileChooser(null, null, null)
    }
}