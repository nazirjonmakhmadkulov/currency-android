package com.developer.currency.ui.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import com.developer.currency.R
import com.developer.currency.core.base.BaseViewHolder
import com.developer.currency.core.base.Item
import com.developer.currency.core.base.ItemBase
import com.developer.currency.core.utils.getImageRes
import com.developer.currency.databinding.ItemConverterBinding
import com.developer.currency.domain.entities.Valute
import timber.log.Timber

class ConverterAdapter(private val onItemChange: (Int, String, String) -> Unit) :
    ItemBase<ItemConverterBinding, Valute> {
    override fun isRelativeItem(item: Item): Boolean = item is Valute
    override fun getLayoutId() = R.layout.item_converter
    override fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        BaseViewHolder<ItemConverterBinding, Valute> {
        val binding = ItemConverterBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding, onItemChange)
    }

    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute) = oldItem.valId == newItem.valId
        override fun areContentsTheSame(oldItem: Valute, newItem: Valute) = oldItem.value == newItem.value
        override fun getChangePayload(oldItem: Valute, newItem: Valute): Any? {
            if (oldItem.value != newItem.value) return newItem.value
            return super.getChangePayload(oldItem, newItem)
        }
    }

    inner class FavoriteViewHolder(
        binding: ItemConverterBinding,
        private val onItemChange: (Int, String, String) -> Unit
    ) : BaseViewHolder<ItemConverterBinding, Valute>(binding) {
        override fun onBind(item: Valute) = with(binding) {
            super.onBind(item)
            moneyConvert.doOnTextChanged { text, _, _, _ ->
                try {
                    onItemChange(item.id, text.toString(), item.value)
                } catch (e: NumberFormatException) {
                    Timber.e("NumberFormatException $e")
                }
            }
            moneyConvert.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) onItemChange(item.id, "0.0", "0.0")
                else {
                    binding.moneyConvert.setText("")
                    binding.moneyConvert.hint = "0.0"
                }
            }
            val drawable = root.context.getImageRes(item.charCode)
            iconValute.setImageDrawable(drawable)
            charCode.text = item.charCode
            moneyConvert.hint = "0.0"
        }

        override fun onBind(item: Valute, payloads: List<Any>) {
            super.onBind(item, payloads)
            if (payloads.isNotEmpty()) binding.moneyConvert.hint = item.value
        }
    }
}