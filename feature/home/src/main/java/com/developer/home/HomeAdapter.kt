package com.developer.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.developer.common.Item
import com.developer.designsystem.base.BaseViewHolder
import com.developer.designsystem.base.ItemBase
import com.developer.designsystem.databinding.ItemValuteBinding
import com.developer.designsystem.icons.getImageRes
import com.developer.domain.model.Currency

class HomeAdapter(private val onItemValuteClick: (Currency) -> Unit) : ItemBase<ItemValuteBinding, Currency> {
    override fun isRelativeItem(item: Item): Boolean = item is Currency
    override fun getLayoutId() = R.layout.item_valute
    override fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        BaseViewHolder<ItemValuteBinding, Currency> {
        val binding = ItemValuteBinding.inflate(layoutInflater, parent, false)
        return ValuteViewHolder(binding, onItemValuteClick)
    }

    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Currency, newItem: Currency) = oldItem == newItem
    }

    inner class ValuteViewHolder(
        binding: ItemValuteBinding, val onItemValuteClick: (Currency) -> Unit
    ) : BaseViewHolder<ItemValuteBinding, Currency>(binding) {
        init {
            binding.cardId.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onItemValuteClick(item)
            }
        }

        @SuppressLint("SetTextI18n")
        override fun onBind(item: Currency) = with(binding) {
            super.onBind(item)
            val drawable = root.context.getImageRes(item.charCode)
            iconValute.setImageDrawable(drawable)
            name.text = item.charCode
            nameCountry.text = item.name
            date.text = item.dates
            somon.text = item.value + " TJS"
            value.text = "${item.nominal} ${item.charCode}"
        }
    }
}