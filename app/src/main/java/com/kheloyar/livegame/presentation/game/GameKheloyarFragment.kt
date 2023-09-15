package com.kheloyar.livegame.presentation.game

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kheloyar.livegame.R
import com.kheloyar.livegame.databinding.FragmentSlotKheloyarBinding
import com.kheloyar.livegame.presentation.Constants
import com.kheloyar.livegame.presentation.Constants.DELAY_WIN_SPIN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameKheloyarFragment() : Fragment() {

    private lateinit var bindingKheloyar: FragmentSlotKheloyarBinding
    private val viewModelKheloyar: GameKheloyarViewModel by viewModel()
    private var firstAdapterKheloyar: AdapterKheloyatItem? = null
    private var secondAdapterKheloyar: AdapterKheloyatItem? = null
    private var thirdAdapterKheloyar: AdapterKheloyatItem? = null
    private lateinit var kheloyarMediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingKheloyar = FragmentSlotKheloyarBinding.inflate(inflater, container, false)
        kheloyarMediaPlayer = MediaPlayer.create(requireContext(), R.raw.win_music)
        return bindingKheloyar.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setKheloyarClickListeners()
        setObserversKheloyar()
        setAdaptersKheloyar()
        setRecyclerViews()
    }

    private fun setAdaptersKheloyar() {
        firstAdapterKheloyar = AdapterKheloyatItem()
        secondAdapterKheloyar = AdapterKheloyatItem()
        thirdAdapterKheloyar = AdapterKheloyatItem()
    }

    private fun setRecyclerViews() {
        bindingKheloyar.apply {
            slotKheloyarRecycleViewFirst.apply {
                layoutManager =
                    object : LinearLayoutManager(context) {
                        override fun canScrollVertically() = false
                    }.apply {
                        orientation = LinearLayoutManager.VERTICAL
                    }
                adapter = firstAdapterKheloyar
                firstAdapterKheloyar?.setItemsKheloyar(viewModelKheloyar.getRandomListOfItems())
            }
            slotKheloyarRecycleViewSecond.apply {
                layoutManager =
                    object : LinearLayoutManager(context) {
                        override fun canScrollVertically() = false
                    }.apply {
                        orientation = LinearLayoutManager.VERTICAL
                    }
                adapter = secondAdapterKheloyar
                secondAdapterKheloyar?.setItemsKheloyar(viewModelKheloyar.getRandomListOfItems())
            }
            slotKheloyarRecycleViewThird.apply {
                layoutManager =
                    object : LinearLayoutManager(context) {
                        override fun canScrollVertically() = false
                    }.apply {
                        orientation = LinearLayoutManager.VERTICAL
                    }
                adapter = thirdAdapterKheloyar
                thirdAdapterKheloyar?.setItemsKheloyar(viewModelKheloyar.getRandomListOfItems())
            }
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
                viewModelKheloyar.changeBetKheloyar(false, requireContext())
            }
            plusButtonKheloyar.setOnClickListener {
                viewModelKheloyar.changeBetKheloyar(true, requireContext())
            }
            kheloyarSpinButton.setOnClickListener {
                onClickSpin()
            }
        }
    }

    private fun onClickSpin() {
        bindingKheloyar.apply {
            if (viewModelKheloyar.onClickSpinButtonKheloyar(requireContext())) {
                firstAdapterKheloyar?.setItemsKheloyar(viewModelKheloyar.getRandomListOfItems())
                secondAdapterKheloyar?.setItemsKheloyar(viewModelKheloyar.getRandomListOfItems())
                thirdAdapterKheloyar?.setItemsKheloyar(viewModelKheloyar.getRandomListOfItems())
                slotKheloyarRecycleViewFirst.scrollToPosition(0)
                slotKheloyarRecycleViewSecond.scrollToPosition(0)
                slotKheloyarRecycleViewThird.scrollToPosition(0)
                slotKheloyarRecycleViewFirst.smoothScrollToPosition(
                    firstAdapterKheloyar?.itemCount ?: 0
                )
                slotKheloyarRecycleViewSecond.smoothScrollToPosition(
                    secondAdapterKheloyar?.itemCount ?: 0
                )
                slotKheloyarRecycleViewThird.smoothScrollToPosition(
                    thirdAdapterKheloyar?.itemCount ?: 0
                )
                viewModelKheloyar.checkWin(
                    firstAdapterKheloyar?.getPreLustItem() ?: 1,
                    secondAdapterKheloyar?.getPreLustItem() ?: 2,
                    thirdAdapterKheloyar?.getPreLustItem() ?: 3
                )
            }
        }
    }

    private fun setObserversKheloyar() {
        viewModelKheloyar.apply {
            coinsKheloyar.observe(viewLifecycleOwner) {
                bindingKheloyar.coinsKheloyarText.text = it.toString()
            }

            betKheloyar.observe(viewLifecycleOwner) {
                bindingKheloyar.betKheloyarText.text = it.toString()
            }

            showSnackbar.observe(viewLifecycleOwner) {
                if (it != "")
                    Snackbar.make(bindingKheloyar.root, it, Snackbar.LENGTH_SHORT).show()
            }
            showWinAnimations.observe(viewLifecycleOwner) {
                if (it) {
                    val animation =
                        AnimationUtils.loadAnimation(context, R.anim.win_animations_kheloyar)
                    if (requireContext().getSharedPreferences(
                            Constants.KHELOYAR_SP,
                            Context.MODE_PRIVATE
                        )
                            .getBoolean(Constants.KHELOYAR_MUSIC, true)
                    ) kheloyarMediaPlayer.start()
                    lifecycleScope.launch {
                        delay(300)
                        withContext(Dispatchers.Main) {
                            bindingKheloyar.winCentralAnimationKheloyar.apply {
                                visibility = View.VISIBLE
                                startAnimation(animation)
                            }
                            bindingKheloyar.winBottomAnimationKheloyar.apply {
                                visibility = View.VISIBLE
                                startAnimation(animation)
                            }
                        }
                        delay(DELAY_WIN_SPIN)
                        viewModelKheloyar.setWinFalse()
                    }
                } else {
                    bindingKheloyar.winCentralAnimationKheloyar.apply {
                        visibility = View.INVISIBLE
                        clearAnimation()
                    }
                    bindingKheloyar.winBottomAnimationKheloyar.apply {
                        visibility = View.INVISIBLE
                        clearAnimation()
                    }
                }
            }
            isClickableSpinKheloyar.observe(viewLifecycleOwner) {
                bindingKheloyar.kheloyarSpinButton.isClickable = it
                if (it)
                    bindingKheloyar.ramkaKheloyar.visibility = View.INVISIBLE
                else bindingKheloyar.ramkaKheloyar.visibility = View.VISIBLE
            }
        }
    }
}