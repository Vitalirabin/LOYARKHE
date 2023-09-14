package com.kheloyar.livegame.presentation.utils

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterfaceKheloyar(private val mContext: Context) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String) {
        if (toast != "")
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        else
            if (toast.length > 2)
                Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
            else Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}