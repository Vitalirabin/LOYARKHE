package com.kheloyar.livegame.presentation.loading

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kheloyar.livegame.R
import com.kheloyar.livegame.databinding.FragmentSplashKheloyarBinding
import com.kheloyar.livegame.domain.KheloyarUseCase
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_SP
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_URL_SP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoadingKheloyarFragment : Fragment() {

    private lateinit var bindingKheloyar: FragmentSplashKheloyarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        bindingKheloyar = FragmentSplashKheloyarBinding.inflate(inflater, container, false)
        return bindingKheloyar.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSimKheloyar()
        bindingKheloyar.kheloyarProgressBar.apply {
            max = 100
            progress += 30
        }
    }

    private fun checkSimKheloyar() {
        val telephonyManager =
            requireContext().getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
        when (telephonyManager.simState) {
            TelephonyManager.SIM_STATE_ABSENT -> {
                nextFragment(true)
            }

            else -> {
                checkAviaKheloyar()
            }
        }
    }

    private fun checkAviaKheloyar() {
        if (Settings.System.getInt(
                requireContext().contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
            ) == 0
        ) nextFragment(true)
        else checkDeveloperSettingsKheloyar()
    }

    private fun checkDeveloperSettingsKheloyar() {
        if (Settings.Secure.getInt(
                requireContext().contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                0
            ) == 1
        ) {
            nextFragment(true)
        } else {
            checkURLKheloyar()
        }

    }

    private fun checkURLKheloyar() {
        CoroutineScope(Dispatchers.IO).launch {
            KheloyarUseCase().getLinkForAppKheloyar("https://kheloyar.sbs/pkHFdv36").onSuccess {
                if (it != "") {
                    requireContext().getSharedPreferences(KHELOYAR_SP, Context.MODE_PRIVATE)
                        .edit().putString(
                            KHELOYAR_URL_SP, it
                        ).apply()
                    nextFragment(false)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    nextFragment(true)
                }
            }
        }
    }

    private suspend fun updateProgress() {
        withContext(Dispatchers.Main) {
            bindingKheloyar.kheloyarProgressBar.progress += 1
        }
        delay(15)
    }

    private fun nextFragment(isGame: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 1..70) {
                updateProgress()
            }
            withContext(Dispatchers.Main) {
                if (isGame) findNavController().navigate(R.id.action_splashKheloyarFragment_to_startKheloyarGameFragment)
                else findNavController().navigate(R.id.action_splashKheloyarFragment_to_kheloyarFragment)
            }
        }
    }
}