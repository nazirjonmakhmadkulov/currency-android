package com.developer.currency.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.developer.currency.R
import com.developer.currency.core.base.BaseViewHolder
import com.developer.currency.core.base.Item
import com.developer.currency.core.base.ItemBase
import com.developer.currency.core.utils.ImageResource
import com.developer.currency.databinding.DialogItemBinding
import com.developer.currency.domain.entities.Valute

class ValDialogAdapter(private val onItemValuteClick: (Valute) -> Unit) : ItemBase<DialogItemBinding, Valute> {
    override fun isRelativeItem(item: Item): Boolean = item is Valute
    override fun getLayoutId() = R.layout.item_valute
    override fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        BaseViewHolder<DialogItemBinding, Valute> {
        val binding = DialogItemBinding.inflate(layoutInflater, parent, false)
        return ValuteViewHolder(binding, onItemValuteClick)
    }

    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Valute, newItem: Valute) = oldItem == newItem
    }

    inner class ValuteViewHolder(binding: DialogItemBinding, val onItemValuteClick: (Valute) -> Unit) :
        BaseViewHolder<DialogItemBinding, Valute>(binding) {
        override fun onBind(item: Valute) = with(binding) {
            super.onBind(item)
            val bt = ImageResource.getImageRes(binding.root.context, item.charCode)
            this.imgFlag.setImageDrawable(bt)
            nameCurrency.text = item.charCode
            itemDialog.setOnClickListener { onItemValuteClick(item) }
        }
    }
}