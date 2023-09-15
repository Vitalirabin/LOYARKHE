package com.kheloyar.livegame.presentation.options

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kheloyar.livegame.R
import com.kheloyar.livegame.databinding.FragmentOptionsKheloyarBinding
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_MUSIC
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_SOUND
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_SP
import org.koin.android.ext.android.inject

class OptionsKheloyarFragment : Fragment() {

    private lateinit var bindingKheloyar: FragmentOptionsKheloyarBinding
    private val kheloyarMP: MediaPlayer by inject()
    private lateinit var sp: SharedPreferences
    private val sharedPreferencesOnChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                KHELOYAR_SOUND -> {
                    updateUIState(
                        sharedPreferences.getBoolean(KHELOYAR_SOUND, false),
                        sharedPreferences.getBoolean(KHELOYAR_MUSIC, false)
                    )
                }

                KHELOYAR_MUSIC -> {
                    updateUIState(
                        sharedPreferences.getBoolean(KHELOYAR_SOUND, false),
                        sharedPreferences.getBoolean(KHELOYAR_MUSIC, false)
                    )
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bindingKheloyar = FragmentOptionsKheloyarBinding.inflate(inflater, container, false)
        sp = requireContext().getSharedPreferences(KHELOYAR_SP, Context.MODE_PRIVATE)
        sp.registerOnSharedPreferenceChangeListener(sharedPreferencesOnChangeListener)
        return bindingKheloyar.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUIState(sp.getBoolean(KHELOYAR_SOUND, true), sp.getBoolean(KHELOYAR_MUSIC, true))
        bindingKheloyar.apply {
            kheloyarSoundButton.setOnClickListener {
                sp.edit().putBoolean(KHELOYAR_SOUND, !sp.getBoolean(KHELOYAR_SOUND, false))
                    .apply()
            }

            kheloyarMusicButton.setOnClickListener {
                sp.edit().putBoolean(KHELOYAR_MUSIC, !sp.getBoolean(KHELOYAR_MUSIC, false))
                    .apply()
            }
            kheloyarCrossButton.setOnClickListener {
                findNavController().popBackStack()
            }
            kheloyarBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun updateUIState(isSound: Boolean, isMusic: Boolean) {
        bindingKheloyar.apply {
            kheloyarSoundButton.apply {
                if (isSound) {
                    kheloyarMP.start()
                    setImageDrawable(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.sound_options_button_vector_kheloyar
                        )
                    )
                } else {
                    kheloyarMP.stop()
                    try {
                        kheloyarMP.apply {
                            prepare()
                            seekTo(0)
                        }
                    } catch (e: Throwable) {
                        Log.e("OptionsKheloyarFragment", e.message, e)
                    }
                    setImageDrawable(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.no_sound_vector_kheloyar
                        )
                    )
                }
            }
            kheloyarMusicButton.apply {
                if (isMusic)
                    setImageDrawable(
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.music_kheloyar_vector
                        )
                    )
                else setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.no_music_vector_kheloyar
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sp.unregisterOnSharedPreferenceChangeListener(sharedPreferencesOnChangeListener)
    }
}