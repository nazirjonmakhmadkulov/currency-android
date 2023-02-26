package com.developer.valyutaapp.ui.adapter

import com.developer.valyutaapp.domain.entities.Valute
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseViewHolder
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.core.base.ItemBase
import com.developer.valyutaapp.core.common.FAVORITE_CONVERTER
import com.developer.valyutaapp.databinding.FavoritesItemBinding
import com.developer.valyutaapp.utils.ImageResource

class FavoriteAdapter(
    private val type: String?,
    private val onItemValuteClick: (Valute, Int) -> Unit,
) : ItemBase<FavoritesItemBinding, Valute> {
    override fun isRelativeItem(item: Item): Boolean = item is Valute
    override fun getLayoutId() = R.layout.item
    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup,
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
        binding: FavoritesItemBinding, val onItemValuteClick: (Valute, Int) -> Unit,
    ) : BaseViewHolder<FavoritesItemBinding, Valute>(binding) {
        override fun onBind(item: Valute) = with(binding) {
            super.onBind(item)
            val bt = ImageResource.getImageRes(binding.root.context, item.charCode)
            iconValute.setImageBitmap(bt)
            type?.let {
                if (type == FAVORITE_CONVERTER) {
                    favorite.setFavorites(item.favoritesConverter)
                    favorite.setOnClickListener {
                        if (item.favoritesConverter == 1) item.favoritesConverter = 0
                        else item.favoritesConverter = 1
                        onItemValuteClick(item, position)
                    }
                } else {
                    favorite.setFavorites(item.favoritesValute)
                    favorite.setOnClickListener {
                        if (item.favoritesValute == 1) item.favoritesValute = 0
                        else item.favoritesValute = 1
                        onItemValuteClick(item, position)
                    }
                }
            }
            nameValute.text = item.name
        }

        override fun onBind(item: Valute, payloads: List<Any>) {
            super.onBind(item, payloads)
//            val inFavorites = payloads.last() as Int
//            binding.favorite.setFavorites(item.favoritesValute)
        }

        private fun ImageView.setFavorites(inFavorites: Int?) {
            if (inFavorites == 1) setImageResource(R.drawable.ic_favorite)
            else setImageResource(R.drawable.ic_unfavorite)
        }
    }
}