package com.developer.currency.ui.valutes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.developer.currency.R
import com.developer.currency.core.base.BaseViewHolder
import com.developer.currency.core.base.Item
import com.developer.currency.core.base.ItemBase
import com.developer.currency.databinding.ItemValuteBinding
import com.developer.currency.domain.entities.Valute
import com.developer.currency.core.utils.ImageResource

class ValCursAdapter(private val onItemValuteClick: (Valute) -> Unit) : ItemBase<ItemValuteBinding, Valute> {
    override fun isRelativeItem(item: Item): Boolean = item is Valute
    override fun getLayoutId() = R.layout.item_valute
    override fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        BaseViewHolder<ItemValuteBinding, Valute> {
        val binding = ItemValuteBinding.inflate(layoutInflater, parent, false)
        return ValuteViewHolder(binding, onItemValuteClick)
    }

    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Valute, newItem: Valute) = oldItem == newItem
    }

    inner class ValuteViewHolder(
        binding: ItemValuteBinding,
        val onItemValuteClick: (Valute) -> Unit
    ) : BaseViewHolder<ItemValuteBinding, Valute>(binding) {
        @SuppressLint("SetTextI18n")
        override fun onBind(item: Valute) = with(binding) {
            super.onBind(item)
            val bt = ImageResource.getImageRes(binding.root.context, item.charCode)
            iconValute.setImageDrawable(bt)
            name.text = item.charCode
            nameCountry.text = item.name
            date.text = item.dates
            somon.text = item.value + " TJS"
            value.text = "${item.nominal} ${item.charCode}"
            cardId.setOnClickListener { onItemValuteClick(item) }
        }
    }
}