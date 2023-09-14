package com.kheloyar.livegame.presentation.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kheloyar.livegame.R
import com.kheloyar.livegame.databinding.FragmentMenuGameKheloyarBinding

class MenuKheloyarGameFragment : Fragment() {

    private lateinit var bindingKheloyar: FragmentMenuGameKheloyarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("StartEsporteGameFragment", "onCreateView")
        bindingKheloyar = FragmentMenuGameKheloyarBinding.inflate(inflater, container, false)
        return bindingKheloyar.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingKheloyar.apply {
            kheloyarOptionsButton.setOnClickListener {
                findNavController().navigate(R.id.action_startKheloyarGameFragment_to_optionsKheloyarFragment)
            }
            kheloyarExitButton.setOnClickListener {
                activity?.finish()
            }
            kheloyarPlayButton.setOnClickListener {
                findNavController().navigate(R.id.action_startKheloyarGameFragment_to_gameKheloyarFragment)
            }
        }
    }
}