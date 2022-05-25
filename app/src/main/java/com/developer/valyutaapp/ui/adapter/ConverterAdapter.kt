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
import com.developer.valyutaapp.databinding.ItemConverterBinding
import com.developer.valyutaapp.utils.ImageResource

class ConverterAdapter(
    private val context: Context,
    private val valutes: MutableList<Valute>,
    private val onItemValuteClick: (Valute, Int) -> Unit,
) :
    RecyclerView.Adapter<ConverterAdapter.ValuteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteHolder {
        val binding =
            ItemConverterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ValuteHolder(binding)
    }

    override fun onBindViewHolder(holder: ValuteHolder, i: Int) {
        (holder as? ValuteHolder)?.bind(valutes[i])
    }

    override fun getItemCount(): Int {
        return valutes.size
    }

    inner class ValuteHolder(private val binding: ItemConverterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(valute: Valute) = with(binding) {
            val bt = ImageResource.getImageRes(context, valute.charCode)
            iconValute.setImageBitmap(bt)
            charCode.text = valute.charCode
            name.text = valute.name
            itemView.setOnClickListener {
                onItemValuteClick(valute, bindingAdapterPosition)
            }
        }
    }
}