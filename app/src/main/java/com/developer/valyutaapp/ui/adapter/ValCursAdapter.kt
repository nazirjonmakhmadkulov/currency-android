package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.developer.valyutaapp.domain.entities.Valute
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.developer.valyutaapp.utils.ImageResource
import com.developer.valyutaapp.databinding.ItemBinding

class ValCursAdapter(
    private val context: Context, private val valutes: MutableList<Valute>,
    private val onItemValuteClick: (Valute, Int) -> Unit,
) :
    RecyclerView.Adapter<ValCursAdapter.ValuteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ValuteHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ValuteHolder, i: Int) {
        (holder as? ValuteHolder)?.bind(valutes[i])
    }

    override fun getItemCount(): Int {
        return valutes.size
    }

    inner class ValuteHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(valute: Valute) = with(binding) {
            val bt = ImageResource.getImageRes(context, valute.charCode)
            iconValute.setImageBitmap(bt)
            name.text = valute.charCode
            nameCountry.text = valute.name
            pokupat.text = "${valute.nominal} ${valute.charCode}"
            prodaj.text = valute.value + " TJS"
            cardId.setOnClickListener {
                onItemValuteClick(valute, bindingAdapterPosition)
            }
        }
    }
}