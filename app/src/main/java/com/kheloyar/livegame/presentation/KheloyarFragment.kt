package com.kheloyar.livegame.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.RenderProcessGoneDetail
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kheloyar.livegame.databinding.FragmentKheloyarBinding
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_SP
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_URL_SP
import com.kheloyar.livegame.presentation.utils.KheloyarChromeClient
import com.kheloyar.livegame.presentation.utils.KheloyarWebClient
import com.kheloyar.livegame.presentation.utils.WebAppInterfaceKheloyar

class KheloyarFragment : Fragment() {

    private lateinit var bindingKheloyar: FragmentKheloyarBinding
    private var uploadMessageKheloyar: ValueCallback<Array<Uri>>? = null

    var startActivityForResultKheloyar =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultKheloyar ->
            uploadMessageKheloyar = if (resultKheloyar.resultCode == AppCompatActivity.RESULT_OK) {
                if (uploadMessageKheloyar == null)
                    return@registerForActivityResult
                uploadMessageKheloyar?.onReceiveValue(
                    WebChromeClient.FileChooserParams.parseResult(
                        resultKheloyar.resultCode,
                        resultKheloyar.data
                    )
                )
                null
            } else {
                uploadMessageKheloyar?.onReceiveValue(null)
                null
            }
        }

    override fun onCreateView(
        inflaterKheloyar: LayoutInflater,
        containerKheloyar: ViewGroup?,
        savedInstanceStateKheloyar: Bundle?
    ): View {
        bindingKheloyar =
            FragmentKheloyarBinding.inflate(layoutInflater, containerKheloyar, false)
        configKheloyar(true)
        val sp = requireActivity().getSharedPreferences(KHELOYAR_SP, Context.MODE_PRIVATE)
        bindingKheloyar.webViewKheloyar.loadUrl(
            sp.getString(KHELOYAR_URL_SP, "") ?: ""
        )
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bindingKheloyar.webViewKheloyar.canGoBack()
                ) {
                    bindingKheloyar.webViewKheloyar.goBack()
                }
            }
        })
        return bindingKheloyar.root
    }


    private fun configKheloyar(enableKheloyar: Boolean) {
        if (enableKheloyar) {
            bindingKheloyar.webViewKheloyar.settings.apply {
                builtInZoomControls = true
                setSupportZoom(true)
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                databaseEnabled = true
                domStorageEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                minimumFontSize = 1
                minimumLogicalFontSize = 1
                allowFileAccessFromFileURLs = true
                allowUniversalAccessFromFileURLs = true
                saveFormData = true
                useWideViewPort = true
                loadWithOverviewMode = true
                builtInZoomControls = true
                displayZoomControls = false
                pluginState = WebSettings.PluginState.OFF
                cacheMode = WebSettings.LOAD_NO_CACHE
                defaultZoom = WebSettings.ZoomDensity.FAR
                loadsImagesAutomatically = true
                allowFileAccess = true
                setRenderPriority(WebSettings.RenderPriority.HIGH)
            }
            bindingKheloyar.webViewKheloyar.apply {
                isScrollbarFadingEnabled = false
                clearView();
                isClickable = true
                isHorizontalScrollBarEnabled = false
                isVerticalScrollBarEnabled = false;
                setInitialScale(1)
                webViewClient = getKheloyarWebClient()
                addJavascriptInterface(
                    WebAppInterfaceKheloyar(requireContext()),
                    "web_interface"
                )
                webChromeClient = getKheloyarChromeClient()
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bindingKheloyar.webViewKheloyar.setRendererPriorityPolicy(
                    WebView.RENDERER_PRIORITY_IMPORTANT,
                    true
                )
            }
            val cookieManagerKheloyar = CookieManager.getInstance()
            cookieManagerKheloyar.setAcceptCookie(true)
            cookieManagerKheloyar.setAcceptThirdPartyCookies(bindingKheloyar.webViewKheloyar, true)
        }
    }

    private fun getKheloyarChromeClient(): KheloyarChromeClient = object : KheloyarChromeClient() {
        override fun onShowFileChooser(
            webViewKheloyar: WebView?,
            filePathCallbackKheloyar: ValueCallback<Array<Uri>>?,
            fileChooserParamsKheloyar: FileChooserParams?
        ): Boolean {
            if (uploadMessageKheloyar != null) {
                uploadMessageKheloyar!!.onReceiveValue(null)
                uploadMessageKheloyar = null
            }
            uploadMessageKheloyar = filePathCallbackKheloyar
            val intent = fileChooserParamsKheloyar?.createIntent()
            try {
                startActivityForResultKheloyar.launch(intent)
            } catch (e: ActivityNotFoundException) {
                uploadMessageKheloyar = null
                return false
            }
            return true
        }

        override fun onConsoleMessage(messageKheloyar: ConsoleMessage): Boolean {
            Log.d(
                "WebView", "${messageKheloyar.message()} -- From line " +
                        "${messageKheloyar.lineNumber()} of ${messageKheloyar.sourceId()}"
            )
            return true
        }
    }

    private fun getKheloyarWebClient(): WebViewClient = object : KheloyarWebClient() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onRenderProcessGone(
            viewKheloyar: WebView,
            detailKheloyar: RenderProcessGoneDetail
        ): Boolean {
            if (!detailKheloyar.didCrash()) {
                bindingKheloyar.webViewKheloyar.also { webViewKheloyar ->
                    val webViewKheloyarContainer: ViewGroup = bindingKheloyar.root
                    webViewKheloyarContainer.removeView(webViewKheloyar)
                    webViewKheloyar.destroy()
                    bindingKheloyar.webViewKheloyar.destroy()
                }
                return true
            }
            return false
        }
    }
}