package com.developer.valyutaapp.adapter

import android.content.Context
import com.developer.valyutaapp.model.Valute
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.developer.valyutaapp.R
import android.view.View
import android.widget.CheckBox
import com.developer.valyutaapp.utils.ImageResource
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class SortAdapter(var valutes: List<Valute>, var context: Context) :
    RecyclerView.Adapter<SortAdapter.ValutesHolder>() {
    private var clickListener: ClickListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ValutesHolder {
        val v =
            LayoutInflater.from(context).inflate(R.layout.sort_item, parent, false)
        return ValutesHolder(v)
    }

    override fun onBindViewHolder(holder: ValutesHolder, i: Int) {
        val bt = ImageResource.getImageRes(context, valutes[i].charCode.toString())
        holder.icon.setImageBitmap(bt)
        holder.tvName.text = valutes[i].charCode
        holder.checkSort.isChecked = valutes[i].sortValute == 1
        holder.checkSort.setOnCheckedChangeListener { compoundButton, b ->
            if (holder.checkSort.isChecked) {
                holder.checkSort.isChecked = true
                clickListener!!.itemClicked(valutes[i], i, 1)
            } else {
                holder.checkSort.isChecked = false
                clickListener!!.itemClicked(valutes[i], i, 0)
            }
        }
    }

    override fun getItemCount(): Int {
        return valutes.size
    }

    inner class ValutesHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvName: TextView
        var icon: ImageView
        var checkSort: CheckBox
        var cardView: CardView

        init {
            icon = v.findViewById<View>(R.id.iconValute) as ImageView
            cardView = v.findViewById<View>(R.id.itemCard) as CardView
            tvName = v.findViewById<View>(R.id.nameValute) as TextView
            checkSort = v.findViewById<View>(R.id.checkSort) as CheckBox
        }
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun itemClicked(item: Valute?, position: Int, sortValute: Int)
    }
}