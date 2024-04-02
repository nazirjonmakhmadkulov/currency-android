package com.developer.currency.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.developer.currency.R
import com.developer.currency.core.base.BaseViewHolder
import com.developer.currency.core.base.Item
import com.developer.currency.core.base.ItemBase
import com.developer.currency.core.common.FAVORITE_CONVERTER
import com.developer.currency.core.utils.getImageRes
import com.developer.currency.databinding.FavoritesItemBinding
import com.developer.currency.domain.entities.Valute

class FavoriteAdapter(
    private val type: String?,
    private val onItemValuteClick: (Valute, Int) -> Unit
) : ItemBase<FavoritesItemBinding, Valute> {
    override fun isRelativeItem(item: Item): Boolean = item is Valute
    override fun getLayoutId() = R.layout.item_valute
    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ): BaseViewHolder<FavoritesItemBinding, Valute> {
        val binding = FavoritesItemBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding, onItemValuteClick)
    }

    override fun getDiffUtil() = diffUtil
    private val diffUtil = object : DiffUtil.ItemCallback<Valute>() {
        override fun areItemsTheSame(oldItem: Valute, newItem: Valute) = oldItem.valId == newItem.valId
        override fun areContentsTheSame(oldItem: Valute, newItem: Valute) = oldItem == newItem
    }

    inner class FavoriteViewHolder(
        binding: FavoritesItemBinding,
        val onItemValuteClick: (Valute, Int) -> Unit
    ) : BaseViewHolder<FavoritesItemBinding, Valute>(binding) {
        override fun onBind(item: Valute) = with(binding) {
            super.onBind(item)
            val drawable = root.context.getImageRes(item.charCode)
            iconValute.setImageDrawable(drawable)
            if (type == FAVORITE_CONVERTER) {
                favorite.setFavorites(item.favoritesConverter)
                favorite.setOnClickListener {
                    item.favoritesConverter = if (item.favoritesConverter == 1) 0 else 1
                    onItemValuteClick(item, bindingAdapterPosition)
                }
            } else {
                favorite.setFavorites(item.favoritesValute)
                favorite.setOnClickListener {
                    item.favoritesValute = if (item.favoritesValute == 1) 0 else 1
                    onItemValuteClick(item, bindingAdapterPosition)
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