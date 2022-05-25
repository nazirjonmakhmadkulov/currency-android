package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.developer.valyutaapp.domain.entities.Valute
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.FAVORITE_CONVERTER
import com.developer.valyutaapp.databinding.FavoritesItemBinding
import com.developer.valyutaapp.utils.ImageResource

class FavoriteAdapter(
    private val context: Context, private val type: String?,
    private val valutes: MutableList<Valute>,
    private val onItemValuteClick: (Valute, Int) -> Unit,
) :
    RecyclerView.Adapter<FavoriteAdapter.ValuteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteHolder {
        val binding =
            FavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ValuteHolder(binding)
    }

    override fun onBindViewHolder(holder: ValuteHolder, i: Int) {
        (holder as? ValuteHolder)?.bind(valutes[i])
    }

    override fun getItemCount(): Int {
        return valutes.size
    }

    inner class ValuteHolder(private val binding: FavoritesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(valute: Valute) = with(binding) {
            val bt = ImageResource.getImageRes(context, valute.charCode)
            iconValute.setImageBitmap(bt)
            nameValute.text = valute.name
            type?.let {
                if (type == FAVORITE_CONVERTER) {
                    favorite.setFavorites(valute.favoritesConverter)
                    favorite.setOnClickListener {
                        if (valute.favoritesConverter == 1) {
                            valute.favoritesConverter = 0
                        } else {
                            valute.favoritesConverter = 1
                        }
                        onItemValuteClick(valute, bindingAdapterPosition)
                    }
                } else {
                    favorite.setFavorites(valute.favoritesValute)
                    favorite.setOnClickListener {
                        if (valute.favoritesValute == 1) {
                            valute.favoritesValute = 0
                        } else {
                            valute.favoritesValute = 1
                        }
                        onItemValuteClick(valute, bindingAdapterPosition)
                    }
                }
            }
        }

        private fun ImageView.setFavorites(inFavorites: Int?) {
            if (inFavorites == 1) {
                setImageResource(R.drawable.ic_favorite)
            } else {
                setImageResource(R.drawable.ic_unfavorite)
            }
        }
    }
}