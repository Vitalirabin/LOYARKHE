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
        fun bind(item: Int) {
            kheloyarBinding.itemImageKheloyar.setImageDrawable(
                AppCompatResources.getDrawable(
                    kheloyarBinding.itemImageKheloyar.context,
                    item
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterKheloyarViewHolder {
        return AdapterKheloyarViewHolder(
            ItemKheloyarRecyclerViewBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int = kheloyarList.size

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()
    override fun onBindViewHolder(holder: AdapterKheloyarViewHolder, position: Int) {
        holder.bind(kheloyarList[position])
    }

    fun setItemsKheloyar(list: List<Int>) {
        kheloyarList = list
        notifyDataSetChanged()
    }

    fun getPreLustItem(): Int {
        return kheloyarList[kheloyarList.lastIndex - 1]
    }
}