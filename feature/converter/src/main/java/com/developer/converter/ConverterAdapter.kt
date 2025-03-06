package com.developer.converter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import com.developer.common.Item
import com.developer.designsystem.base.BaseViewHolder
import com.developer.designsystem.base.ItemBase
import com.developer.designsystem.databinding.ItemConverterBinding
import com.developer.designsystem.icons.getImageRes
import com.developer.domain.model.Currency
import timber.log.Timber

class ConverterAdapter(private val onItemChange: (Int, String, Int, String) -> Unit) :
    ItemBase<ItemConverterBinding, Currency> {
    override fun isRelativeItem(item: Item): Boolean = item is Currency
    override fun getLayoutId() = R.layout.item_converter
    override fun getViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup):
        BaseViewHolder<ItemConverterBinding, Currency> {
        val binding = ItemConverterBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding, onItemChange)
    }

    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency) = oldItem.valId == newItem.valId
        override fun areContentsTheSame(oldItem: Currency, newItem: Currency) = oldItem.value == newItem.value
        override fun getChangePayload(oldItem: Currency, newItem: Currency): Any? {
            if (oldItem.value != newItem.value) return newItem.value
            return super.getChangePayload(oldItem, newItem)
        }
    }

    inner class FavoriteViewHolder(
        binding: ItemConverterBinding,
        private val onItemChange: (Int, String, Int, String) -> Unit
    ) : BaseViewHolder<ItemConverterBinding, Currency>(binding) {
        override fun onBind(item: Currency) = with(binding) {
            super.onBind(item)
            moneyConvert.doOnTextChanged { text, _, _, _ ->
                try {
                    onItemChange(item.id, text.toString(), item.nominal, item.value)
                } catch (e: NumberFormatException) {
                    Timber.e("NumberFormatException $e")
                }
            }
            moneyConvert.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) onItemChange(item.id, "0.0", 0, "0.0")
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

        override fun onBind(item: Currency, payloads: List<Any>) {
            super.onBind(item, payloads)
            if (payloads.isNotEmpty()) binding.moneyConvert.hint = item.value
        }
    }
}