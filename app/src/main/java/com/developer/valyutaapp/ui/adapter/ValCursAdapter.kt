package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import com.developer.valyutaapp.domain.entities.Valute
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseViewHolder
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.core.base.ItemBase
import com.developer.valyutaapp.databinding.ItemBinding
import com.developer.valyutaapp.utils.ImageResource

class ValCursAdapter(
    private val onItemValuteClick: (Valute) -> Unit,
) : ItemBase<ItemBinding, Valute> {
    override fun isRelativeItem(item: Item): Boolean = item is Valute
    override fun getLayoutId() = R.layout.item
    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup,
    ): BaseViewHolder<ItemBinding, Valute> {
        val binding = ItemBinding.inflate(layoutInflater, parent, false)
        return ValuteViewHolder(binding, onItemValuteClick)
    }
    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Valute, newItem: Valute) = oldItem == newItem
    }
    inner class ValuteViewHolder(
        binding: ItemBinding, val onItemValuteClick: (Valute) -> Unit,
    ) : BaseViewHolder<ItemBinding, Valute>(binding) {
        @SuppressLint("SetTextI18n")
        override fun onBind(item: Valute) = with(binding) {
            super.onBind(item)
            val bt = ImageResource.getImageRes(binding.root.context, item.charCode)
            iconValute.setImageBitmap(bt)
            name.text = item.charCode
            nameCountry.text = item.name
            date.text = item.dates
            somon.text = item.value + " TJS"
            value.text = "${item.nominal} ${item.charCode}"
            cardId.setOnClickListener { onItemValuteClick(item) }
        }
    }
}