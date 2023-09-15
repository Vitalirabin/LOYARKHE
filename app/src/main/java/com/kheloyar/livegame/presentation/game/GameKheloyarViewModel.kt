package com.kheloyar.livegame.presentation.game

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kheloyar.livegame.R
import com.kheloyar.livegame.presentation.Constants
import com.kheloyar.livegame.presentation.Constants.DELAY_SPIN
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random

class GameKheloyarViewModel : ViewModel() {

    val coinsKheloyar = MutableLiveData(1500)
    var betKheloyar = MutableLiveData(10)
    val showSnackbar = MutableLiveData("")
    val showWinAnimations = MutableLiveData(false)
    val isClickableSpinKheloyar = MutableLiveData(true)

    private var listOfItems = listOf<Int>()

    fun changeBetKheloyar(up: Boolean, context: Context) {
        if (up)
            betKheloyar.value = betKheloyar.value?.plus(5)
        else
            if (betKheloyar.value!! > 5) betKheloyar.value = betKheloyar.value?.minus(5)
            else showSnackbar.value = context.getString(R.string.minimun_bid_kheloyar)
    }

    fun onClickSpinButtonKheloyar(context: Context): Boolean {
        if (coinsKheloyar.value == 0) {
            showSnackbar.value = context.getString(R.string.ran_out_of_coins_kheloyar)
            return false
        }
        if (coinsKheloyar.value!! < betKheloyar.value!!) {
            showSnackbar.value = context.getString(R.string.not_enought_coins_kheloyar)
            return false
        }
        coinsKheloyar.value = coinsKheloyar.value!! - betKheloyar.value!!
        return true
    }

    fun checkWin(first: Int, second: Int, third: Int) {
        viewModelScope.launch {
            isClickableSpinKheloyar.value = false
            delay(DELAY_SPIN)
            if ((first == second && first == third)) {
                showWinAnimations.value = true
                coinsKheloyar.value = coinsKheloyar.value!! + betKheloyar.value!! * 3
                delay(Constants.DELAY_WIN_SPIN)
                delay(300)
                isClickableSpinKheloyar.value = true
            } else
                isClickableSpinKheloyar.value = true
        }
    }

    fun setWinFalse() {
        showWinAnimations.value = false
    }

    private fun setListOfItems() {
        listOfItems =
            arrayOf(
                R.drawable.vector_chery_kheloyar,
                R.drawable.vector_crown_kheloyar,
                R.drawable.vector_hat_kheloyar,
                R.drawable.vector_heart_kheloyar,
                R.drawable.vector_kheloyar_coin,
                R.drawable.vector_kheloyar_crystal,
                R.drawable.vector_kheloyar_star
            ).toList()
    }

    fun getRandomListOfItems(): List<Int> {
        setListOfItems()
        val result = listOfItems.toMutableList()
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        result.addAll(listOfItems)
        return result.shuffled(Random())
    }
}