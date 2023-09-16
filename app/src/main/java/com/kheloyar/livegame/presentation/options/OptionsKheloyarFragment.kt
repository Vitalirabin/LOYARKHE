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
import androidx.navigation.findNavController
import com.kheloyar.livegame.R
import com.kheloyar.livegame.databinding.FragmentOptionsKheloyarBinding
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_MUSIC
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_SOUND
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_SP
import org.koin.android.ext.android.inject

class OptionsKheloyarFragment : Fragment() {

    private lateinit var bindingKheloyar: FragmentOptionsKheloyarBinding
    private val kheloyarMP: MediaPlayer by inject()
    private lateinit var spKheloyar: SharedPreferences
    private var sharedPreferencesOnChangeListenerKheloyar: SharedPreferences.OnSharedPreferenceChangeListener? =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferencesKheloyar, keyKheloyar ->
            when (keyKheloyar) {
                KHELOYAR_SOUND -> {
                    updateUIState(
                        sharedPreferencesKheloyar.getBoolean(KHELOYAR_SOUND, false),
                        sharedPreferencesKheloyar.getBoolean(KHELOYAR_MUSIC, false)
                    )
                }

                KHELOYAR_MUSIC -> {
                    updateUIState(
                        sharedPreferencesKheloyar.getBoolean(KHELOYAR_SOUND, false),
                        sharedPreferencesKheloyar.getBoolean(KHELOYAR_MUSIC, false)
                    )
                }
            }
        }

    override fun onCreateView(
        inflaterKheloyar: LayoutInflater,
        containerKheloyar: ViewGroup?,
        savedInstanceStateKheloyar: Bundle?
    ): View {
        bindingKheloyar =
            FragmentOptionsKheloyarBinding.inflate(inflaterKheloyar, containerKheloyar, false)
        spKheloyar = requireContext().getSharedPreferences(KHELOYAR_SP, Context.MODE_PRIVATE)
        spKheloyar.registerOnSharedPreferenceChangeListener(
            sharedPreferencesOnChangeListenerKheloyar
        )
        return bindingKheloyar.root
    }

    override fun onViewCreated(viewKheloyar: View, savedInstanceStateKheloyar: Bundle?) {
        super.onViewCreated(viewKheloyar, savedInstanceStateKheloyar)
        updateUIState(
            spKheloyar.getBoolean(KHELOYAR_SOUND, true),
            spKheloyar.getBoolean(KHELOYAR_MUSIC, true)
        )
        bindingKheloyar.apply {
            kheloyarSoundButton.setOnClickListener {
                spKheloyar.edit()
                    .putBoolean(KHELOYAR_SOUND, !spKheloyar.getBoolean(KHELOYAR_SOUND, false))
                    .apply()
            }

            kheloyarMusicButton.setOnClickListener {
                spKheloyar.edit()
                    .putBoolean(KHELOYAR_MUSIC, !spKheloyar.getBoolean(KHELOYAR_MUSIC, false))
                    .apply()
            }
            kheloyarCrossButton.setOnClickListener {
                requireView().findNavController().popBackStack()
            }
            kheloyarBackButton.setOnClickListener {
                requireView().findNavController().popBackStack()
            }
        }
    }

    private fun updateUIState(isSoundKheloyar: Boolean, isMusicKheloyar: Boolean) {
        bindingKheloyar.apply {
            kheloyarSoundButton.apply {
                if (isSoundKheloyar) {
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
                if (isMusicKheloyar)
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
        if (spKheloyar.all.isNotEmpty())
            spKheloyar.unregisterOnSharedPreferenceChangeListener(
                sharedPreferencesOnChangeListenerKheloyar
            )
        sharedPreferencesOnChangeListenerKheloyar =
            if (sharedPreferencesOnChangeListenerKheloyar != null)
                null
            else SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferencesKheloyar, keyKheloyar -> }
    }
}