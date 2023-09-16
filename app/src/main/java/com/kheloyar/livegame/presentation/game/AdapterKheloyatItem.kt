package com.kheloyar.livegame.presentation.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.kheloyar.livegame.databinding.ItemKheloyarRecyclerViewBinding

class AdapterKheloyatItem : RecyclerView.Adapter<AdapterKheloyatItem.AdapterKheloyarViewHolder>() {

    private var kheloyarList = listOf<Int>()

    class AdapterKheloyarViewHolder(private val kheloyarBinding: ItemKheloyarRecyclerViewBinding) :
        RecyclerView.ViewHolder(kheloyarBinding.root) {
        fun kheloyarBind(itemKheloyar: Int) {
            kheloyarBinding.itemImageKheloyar.setImageDrawable(
                AppCompatResources.getDrawable(
                    kheloyarBinding.itemImageKheloyar.context,
                    itemKheloyar
                )
            )
        }
    }

    override fun onCreateViewHolder(parentKheloyar: ViewGroup, viewTypeKheloyar: Int): AdapterKheloyarViewHolder {
        return AdapterKheloyarViewHolder(
            ItemKheloyarRecyclerViewBinding.inflate(
                LayoutInflater.from(
                    parentKheloyar.context
                ), parentKheloyar, false
            )
        )
    }

    override fun getItemCount(): Int = kheloyarList.size

    override fun getItemViewType(positionKheloyar: Int): Int = positionKheloyar

    override fun getItemId(positionKheloyar: Int): Long = positionKheloyar.toLong()
    override fun onBindViewHolder(holderKheloyar: AdapterKheloyarViewHolder, positionKheloyar: Int) {
        holderKheloyar.kheloyarBind(kheloyarList[positionKheloyar])
    }

    fun setItemsKheloyar(listKheloyar: List<Int>) {
        kheloyarList = listKheloyar
        notifyDataSetChanged()
    }

    fun getPreLustItem(): Int {
        return kheloyarList[kheloyarList.lastIndex - 1]
    }
}