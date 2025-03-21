package com.developer.favorite.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.developer.common.FAVORITE_CONVERTER
import com.developer.common.Item
import com.developer.designsystem.base.BaseViewHolder
import com.developer.designsystem.base.ItemBase
import com.developer.designsystem.icons.getImageRes
import com.developer.domain.model.Currency
import com.developer.favorite.R
import com.developer.favorite.databinding.FavoritesItemBinding

class FavoriteAdapter(
    private val type: String?,
    private val onItemCurrencyClick: (Currency, Int) -> Unit
) : ItemBase<FavoritesItemBinding, Currency> {
    override fun isRelativeItem(item: Item): Boolean = item is Currency
    override fun getLayoutId() = R.layout.item_valute
    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ): BaseViewHolder<FavoritesItemBinding, Currency> {
        val binding = FavoritesItemBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding, onItemCurrencyClick)
    }

    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency) = oldItem.valId == newItem.valId
        override fun areContentsTheSame(oldItem: Currency, newItem: Currency) = oldItem == newItem
    }

    inner class FavoriteViewHolder(
        binding: FavoritesItemBinding,
        val onItemCurrencyClick: (Currency, Int) -> Unit
    ) : BaseViewHolder<FavoritesItemBinding, Currency>(binding) {
        override fun onBind(item: Currency) = with(binding) {
            super.onBind(item)
            val drawable = root.context.getImageRes(item.charCode)
            iconValute.setImageDrawable(drawable)
            if (type == FAVORITE_CONVERTER) {
                favorite.setFavorites(item.favoritesConverter)
                favorite.setOnClickListener {
                    item.favoritesConverter = if (item.favoritesConverter == 1) 0 else 1
                    onItemCurrencyClick(item, layoutPosition)
                }
            } else {
                favorite.setFavorites(item.favoritesValute)
                favorite.setOnClickListener {
                    item.favoritesValute = if (item.favoritesValute == 1) 0 else 1
                    onItemCurrencyClick(item, layoutPosition)
                }
            }
            nameValute.text = item.name
        }

        private fun ImageView.setFavorites(inFavorites: Int?) {
            if (inFavorites == 1) setImageResource(R.drawable.ic_favorite)
            else setImageResource(R.drawable.ic_unfavorite)
        }
    }
}