package com.kheloyar.livegame.presentation

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kheloyar.livegame.R
import org.koin.android.ext.android.inject

class MainKheloyarActivity : AppCompatActivity() {

    private val kheloyarMediaPlayer: MediaPlayer by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStop() {
        super.onStop()
        kheloyarMediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        if (getSharedPreferences(Constants.KHELOYAR_SP, Context.MODE_PRIVATE)
                .getBoolean(Constants.KHELOYAR_SOUND, false)
        ) kheloyarMediaPlayer.start()
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