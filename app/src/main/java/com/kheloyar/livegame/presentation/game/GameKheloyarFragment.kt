package com.kheloyar.livegame.presentation.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kheloyar.livegame.R
import com.kheloyar.livegame.databinding.FragmentSlotKheloyarBinding

class GameKheloyarFragment() : Fragment() {

    private lateinit var bindingKheloyar: FragmentSlotKheloyarBinding
    private var coinsKheloyar: Int = 1500
    private var betKheloyar: Int = 20

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingKheloyar = FragmentSlotKheloyarBinding.inflate(inflater, container, false)
        return bindingKheloyar.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setKheloyarClickListeners()
        bindingKheloyar.apply {
            coinsKheloyarText.text = coinsKheloyar.toString()
            betKheloyarText.text = betKheloyar.toString()
        }
    }


    private fun setKheloyarClickListeners() {
        bindingKheloyar.apply {
            kheloyarCrossSlotButton.setOnClickListener {
                findNavController().popBackStack()
            }
            kheloyarMenuSlotButton.setOnClickListener {
                findNavController().popBackStack()
            }
            minusButtonKheloyar.setOnClickListener {
                changeBetKheloyar(false)
            }
            plusButtonKheloyar.setOnClickListener {
                changeBetKheloyar(true)
            }
            kheloyarSpinButton.setOnClickListener {
                onClickSpinButtonKheloyar()
            }
        }
    }

    private fun onClickSpinButtonKheloyar() {
        if (coinsKheloyar == 0) {
            Snackbar.make(
                requireView(),
                getString(R.string.ran_out_of_coins_kheloyar),
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        if (coinsKheloyar < betKheloyar) {
            Snackbar.make(
                requireView(),
                getString(R.string.not_enought_coins_kheloyar),
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }

    }

    private fun changeBetKheloyar(up: Boolean) {
        if (up)
            betKheloyar += 20
        else
            if (betKheloyar > 20) betKheloyar -= 20
            else Snackbar.make(
                requireView(),
                getString(R.string.minimun_biud_kheloyar),
                Snackbar.LENGTH_SHORT
            )
                .show()
        bindingKheloyar.betKheloyarText.text = betKheloyar.toString()
    }
}