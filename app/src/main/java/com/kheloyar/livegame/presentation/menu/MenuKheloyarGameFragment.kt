package com.kheloyar.livegame.presentation.menu

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kheloyar.livegame.R
import com.kheloyar.livegame.databinding.FragmentMenuGameKheloyarBinding
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_SOUND
import com.kheloyar.livegame.presentation.Constants.KHELOYAR_SP
import org.koin.android.ext.android.inject

class MenuKheloyarGameFragment : Fragment() {

    private lateinit var bindingKheloyar: FragmentMenuGameKheloyarBinding
    private val kheloyarMP: MediaPlayer by inject()

    override fun onCreateView(
        inflaterKheloyar: LayoutInflater,
        containerKheloyar: ViewGroup?,
        savedInstanceStateKheloyar: Bundle?
    ): View {
        bindingKheloyar =
            FragmentMenuGameKheloyarBinding.inflate(inflaterKheloyar, containerKheloyar, false)
        if (requireContext().getSharedPreferences(KHELOYAR_SP, Context.MODE_PRIVATE)
                .getBoolean(KHELOYAR_SOUND, true)
        ) kheloyarMP.start()
        return bindingKheloyar.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingKheloyar.apply {
            kheloyarOptionsButton.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_startKheloyarGameFragment_to_optionsKheloyarFragment)
            }
            kheloyarExitButton.setOnClickListener {
                activity?.finish()
            }
            kheloyarPlayButton.setOnClickListener {
                requireView().findNavController().navigate(R.id.action_startKheloyarGameFragment_to_gameKheloyarFragment)
            }
        }
    }
}