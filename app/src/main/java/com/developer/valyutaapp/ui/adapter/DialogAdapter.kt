package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.developer.valyutaapp.domain.entities.Valute
import android.widget.BaseAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import com.developer.valyutaapp.R
import androidx.cardview.widget.CardView
import android.widget.TextView
import android.view.View
import android.widget.ImageView
import com.developer.valyutaapp.utils.ImageResource

class DialogAdapter(
    private val context: Context,
    private val valutes: MutableList<Valute>,
    private val onItemValuteClick: (Valute, Int) -> Unit,
) : BaseAdapter() {

    override fun getCount(): Int {
        return valutes.size
    }

    override fun getItem(i: Int): Any {
        return valutes[i]
    }

    override fun getItemId(i: Int): Long {
        return valutes[i].id.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.dialog_item, viewGroup, false)
        val cardView = v.findViewById<View>(R.id.item_dialog) as CardView
        //val listView = v.findViewById<View>(R.id.list_dialog) as ListView
        val icon = v.findViewById<View>(R.id.img_flag) as ImageView
        val txName = v.findViewById<View>(R.id.name_currency) as TextView
        val bt = ImageResource.getImageRes(context, valutes[i].charCode)
        icon.setImageBitmap(bt)
        txName.text = valutes[i].name
        cardView.setOnClickListener {
            onItemValuteClick(valutes[i], i)
        }
        return v
    }
}