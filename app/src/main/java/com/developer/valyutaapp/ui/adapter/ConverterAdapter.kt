package com.developer.valyutaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseViewHolder
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.core.base.ItemBase
import com.developer.valyutaapp.databinding.ItemConverterBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.utils.ImageResource
import com.developer.valyutaapp.utils.Utils

class ConverterAdapter : ItemBase<ItemConverterBinding, Valute> {
    override fun isRelativeItem(item: Item): Boolean = item is Valute
    override fun getLayoutId() = R.layout.item_converter
    override fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
            BaseViewHolder<ItemConverterBinding, Valute> {
        val binding = ItemConverterBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute) = oldItem.valId == newItem.valId
        override fun areContentsTheSame(oldItem: Valute, newItem: Valute) = oldItem.value == newItem.value
    }

    inner class FavoriteViewHolder(binding: ItemConverterBinding) :
        BaseViewHolder<ItemConverterBinding, Valute>(binding) {
        override fun onBind(item: Valute) = with(binding) {
            super.onBind(item)
            val bt = ImageResource.getImageRes(binding.root.context, item.charCode)
            iconValute.setImageDrawable(bt)
            charCode.text = item.charCode
            moneyConvert.setText(item.value)
        }

        override fun onBind(item: Valute, payloads: List<Any>) {
            super.onBind(item, payloads)
            binding.name.text = item.value
        }
    }
}