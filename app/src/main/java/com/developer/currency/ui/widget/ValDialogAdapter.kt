package com.developer.currency.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.developer.common.Item
import com.developer.currency.R
import com.developer.currency.databinding.DialogItemBinding
import com.developer.designsystem.base.BaseViewHolder
import com.developer.designsystem.base.ItemBase
import com.developer.designsystem.icons.getImageRes
import com.developer.domain.model.Currency

class ValDialogAdapter(
    private val onItemValuteClick: (Currency) -> Unit,
) : ItemBase<DialogItemBinding, Currency> {
    override fun isRelativeItem(item: Item): Boolean = item is Currency

    override fun getLayoutId() = R.layout.item_valute

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<DialogItemBinding, Currency> {
        val binding = DialogItemBinding.inflate(layoutInflater, parent, false)
        return ValuteViewHolder(binding, onItemValuteClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil =
        object : DiffUtil.ItemCallback<Currency>() {
            override fun areItemsTheSame(
                oldItem: Currency,
                newItem: Currency,
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Currency,
                newItem: Currency,
            ) = oldItem == newItem
        }

    inner class ValuteViewHolder(
        binding: DialogItemBinding,
        val onItemValuteClick: (Currency) -> Unit,
    ) : BaseViewHolder<DialogItemBinding, Currency>(binding) {
        init {
            binding.itemDialog.setOnClickListener {
                if (layoutPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onItemValuteClick(item)
            }
        }

        override fun onBind(item: Currency) =
            with(binding) {
                super.onBind(item)
                val drawable = root.context.getImageRes(item.charCode)
                this.imgFlag.setImageDrawable(drawable)
                nameCurrency.text = item.charCode
            }
    }
}