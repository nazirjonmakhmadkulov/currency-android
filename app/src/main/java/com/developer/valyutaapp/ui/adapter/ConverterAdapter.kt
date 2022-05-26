package com.developer.valyutaapp.ui.adapter

import android.content.Context
import com.developer.valyutaapp.domain.entities.Valute
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseViewHolder
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.core.base.ItemBase
import com.developer.valyutaapp.databinding.ItemConverterBinding
import com.developer.valyutaapp.utils.ImageResource

class ConverterAdapter(
    private val onItemValuteClick: (Valute) -> Unit,
) : ItemBase<ItemConverterBinding, Valute> {
    override fun isRelativeItem(item: Item): Boolean = item is Valute
    override fun getLayoutId() = R.layout.item_converter
    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup,
    ): BaseViewHolder<ItemConverterBinding, Valute> {
        val binding = ItemConverterBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding, onItemValuteClick)
    }
    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Valute, newItem: Valute) = oldItem == newItem
    }
    inner class FavoriteViewHolder(binding: ItemConverterBinding, val onItemValuteClick: (Valute) -> Unit,
    ) : BaseViewHolder<ItemConverterBinding, Valute>(binding) {
        override fun onBind(item: Valute) = with(binding) {
            super.onBind(item)
            val bt = ImageResource.getImageRes(binding.root.context, item.charCode)
            iconValute.setImageBitmap(bt)
            charCode.text = item.charCode
            name.text = item.name
            itemView.setOnClickListener {
                onItemValuteClick(item)
            }
        }
    }
}