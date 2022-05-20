package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.developer.valyutaapp.domain.entities.Valute
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.developer.valyutaapp.databinding.FavoritesItemBinding
import com.developer.valyutaapp.utils.ImageResource

class SortAdapter(
    private val context: Context, private val valutes: MutableList<Valute>,
    private val onItemValuteClick: (Valute, Int, Int) -> Unit,
) :
    RecyclerView.Adapter<SortAdapter.ValuteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteHolder {
        val binding = FavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            nameValute.text = valute.charCode
            checkSort.isChecked = valute.sortValute == 1
            checkSort.setOnCheckedChangeListener { _, _ ->
                if (checkSort.isChecked) {
                    checkSort.isChecked = true
                    onItemValuteClick(valute, bindingAdapterPosition, 1)
                } else {
                    checkSort.isChecked = false
                    onItemValuteClick(valute, bindingAdapterPosition, 0)
                }
            }
        }
    }
}