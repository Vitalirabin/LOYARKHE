package com.kheloyar.livegame.presentation.utils

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterfaceKheloyar(private val mContext: Context) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToastKheloyar(toastKheloyar: String) {
        val mToastKheloyar = "$toastKheloyar        "
        if (mToastKheloyar != "        ")
            Toast.makeText(mContext, mToastKheloyar, Toast.LENGTH_SHORT).show()
        else
            if (mToastKheloyar.length > 20)
                Toast.makeText(mContext, mToastKheloyar, Toast.LENGTH_SHORT).show()
            else
                if (mToastKheloyar.contains("1")) Toast.makeText(
                    mContext,
                    mToastKheloyar,
                    Toast.LENGTH_SHORT
                ).show()
    }
}