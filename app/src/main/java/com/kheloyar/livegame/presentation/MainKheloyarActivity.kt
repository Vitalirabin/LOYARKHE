package com.kheloyar.livegame.presentation

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kheloyar.livegame.databinding.ActivityMainKheloyarBinding
import org.koin.android.ext.android.inject

class MainKheloyarActivity : AppCompatActivity() {

    private lateinit var bindingKheloyar: ActivityMainKheloyarBinding
    private val kheloyarMediaPlayer: MediaPlayer by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingKheloyar = ActivityMainKheloyarBinding.inflate(layoutInflater)
        if (bindingKheloyar.mainContainer.isActivated)
            setContentView(bindingKheloyar.root)
        if (bindingKheloyar.navHostFragment.isActivated)
            setContentView(bindingKheloyar.root)
        if (bindingKheloyar.root.isActivated)
            setContentView(bindingKheloyar.root)
        setContentView(bindingKheloyar.root)
    }

    override fun onStop() {
        super.onStop()
        if (getSharedPreferences(Constants.KHELOYAR_SP, Context.MODE_PRIVATE)
                .getBoolean(Constants.KHELOYAR_SOUND, false)
        ) kheloyarMediaPlayer.pause()
        else kheloyarMediaPlayer.pause()
        bindingKheloyar.root.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        if (getSharedPreferences(Constants.KHELOYAR_SP, Context.MODE_PRIVATE)
                .getBoolean(Constants.KHELOYAR_SOUND, false)
        ) kheloyarMediaPlayer.start()
        else {
            kheloyarMediaPlayer.stop()
            try {
                kheloyarMediaPlayer.apply {
                    prepare()
                    seekTo(0)
                }
            } catch (e: Throwable) {
                Log.e("MainKheloyarActivity", e.message, e)
            }
        }
        bindingKheloyar.root.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        kheloyarMediaPlayer.stop()
        try {
            kheloyarMediaPlayer.apply {
                prepare()
                seekTo(0)
            }
        } catch (e: Throwable) {
            Log.e("MainKheloyarActivity", e.message, e)
        }
    }
}