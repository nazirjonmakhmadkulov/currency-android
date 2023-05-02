package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.developer.valyutaapp.R
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.utils.ImageResource

class DialogAdapter(
    private val context: Context,
    private val valutes: MutableList<Valute>,
    //private val onItemValuteClick: (Valute, Int) -> Unit,
) : BaseAdapter() {
    override fun getCount(): Int =  valutes.size
    override fun getItem(i: Int): Any = valutes[i]
    override fun getItemId(i: Int): Long = valutes[i].id.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.dialog_item, viewGroup, false)
        val cardView = v.findViewById<CardView>(R.id.item_dialog)
        //val listView = v.findViewById<ListView>(R.id.list_dialog)
        val icon = v.findViewById<ImageView>(R.id.img_flag)
        val txName = v.findViewById<TextView>(R.id.name_currency)
        val bt = ImageResource.getImageRes(context, valutes[i].charCode)
        icon.setImageDrawable(bt)
        txName.text = valutes[i].name
        //cardView.setOnClickListener { onItemValuteClick(valutes[i], i) }
        return v
    }
}