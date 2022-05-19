package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.developer.valyutaapp.domain.entities.Valute
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.developer.valyutaapp.R
import android.view.View
import android.widget.ImageView
import com.developer.valyutaapp.utils.ImageResource
import android.widget.TextView
import androidx.cardview.widget.CardView

class ValCursAdapter(var valutes: List<Valute>, var context: Context) :
    RecyclerView.Adapter<ValCursAdapter.ValutesHolder>() {

    private var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValutesHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ValutesHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ValutesHolder, i: Int) {
        val bt = ImageResource.getImageRes(context, valutes[i].charCode)
        holder.icon.setImageBitmap(bt)
        holder.tvName.text = valutes[i].charCode
        holder.tvYear.text = valutes[i].name
        holder.tvValue.text = "${valutes[i].nominal} ${valutes[i].charCode}"
        holder.tvNominal.text = valutes[i].value + " TJS"
        holder.cardView.setOnClickListener {
            clickListener!!.itemClicked(valutes[i], i)
        }
    }

    override fun getItemCount(): Int {
        return valutes.size
    }

    inner class ValutesHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvName: TextView
        var tvYear: TextView
        var tvValue: TextView
        var tvNominal: TextView
        var icon: ImageView
        var cardView: CardView

        init {
            icon = v.findViewById<View>(R.id.iconValute) as ImageView
            cardView = v.findViewById<View>(R.id.cardId) as CardView
            tvName = v.findViewById<View>(R.id.name) as TextView
            tvYear = v.findViewById<View>(R.id.nameCountry) as TextView
            tvValue = v.findViewById<View>(R.id.pokupat) as TextView
            tvNominal = v.findViewById<View>(R.id.prodaj) as TextView
        }
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun itemClicked(item: Valute?, position: Int)
    }
}