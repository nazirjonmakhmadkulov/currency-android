package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.valyutaapp.databinding.ItemConverterBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.utils.ImageResource

class ConAdapter(
    private val onChangeValute: (Double, Int) -> Unit,
    private val onItemValuteClick: (Valute) -> Unit,
) : RecyclerView.Adapter<ConAdapter.ConvertViewHolder>() {
    private var items: ArrayList<Valute> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvertViewHolder {
        val binding =
            ItemConverterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConvertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConvertViewHolder, position: Int) {
        holder.bind(valute = items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setValute(medias: ArrayList<Valute>) {
        items = medias
        notifyDataSetChanged()
    }

    inner class ConvertViewHolder(private val binding: ItemConverterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(valute: Valute) = with(binding) {
            val bt = ImageResource.getImageRes(root.context, valute.charCode)
            iconValute.setImageBitmap(bt)
            charCode.text = valute.charCode
            name.text = valute.value
            moneyConvert.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s!!.isNotBlank()) {
                        onChangeValute(s.toString().toDouble(), bindingAdapterPosition)
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            itemView.setOnClickListener {
                onItemValuteClick(valute)
            }
        }
    }
}