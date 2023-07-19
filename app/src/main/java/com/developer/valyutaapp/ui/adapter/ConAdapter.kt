package com.developer.valyutaapp.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.valyutaapp.databinding.ItemConverterBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.utils.ImageResource

class ConAdapter(
    private var items: ArrayList<Valute>,
    private val onChangeValute: (Double, Int) -> Unit,
    private val onItemValuteClick: (Valute) -> Unit,
) : RecyclerView.Adapter<ConAdapter.ConvertViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvertViewHolder {
        val binding = ItemConverterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConvertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConvertViewHolder, position: Int) {
        holder.bind(valute = items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ConvertViewHolder(private val binding: ItemConverterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(valute: Valute) = with(binding) {
            val bt = ImageResource.getImageRes(root.context, valute.charCode)
            iconValute.setImageDrawable(bt)
            charCode.text = valute.charCode
            name.text = valute.value
            moneyConvert.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty()) onChangeValute(s.toString().toDouble(), adapterPosition)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            itemView.setOnClickListener { onItemValuteClick(valute) }
        }
    }
}