package com.developer.currency.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.developer.currency.R
import com.developer.currency.domain.entities.Valute
import com.developer.currency.utils.ImageResource

class WidgetAdapter(
    private val context: Context,
    private val valutes: MutableList<Valute>,
    private val onItemValuteClick: (Valute, Int) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = valutes.size
    override fun getItem(i: Int): Any = valutes[i]
    override fun getItemId(i: Int): Long = valutes[i].id.toLong()

    @SuppressLint("MissingInflatedId")
    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.favorites_item, viewGroup, false)
        val linearLayout = v.findViewById<View>(R.id.item_dialog) as LinearLayout
        // val listView = v.findViewById<View>(R.id.list_dialog) as ListView
        val icon = v.findViewById<View>(R.id.img_flag) as ImageView
        val tvName = v.findViewById<View>(R.id.name_currency) as TextView
        val bt = ImageResource.getImageRes(context, valutes[i].charCode)
        icon.setImageDrawable(bt)
        tvName.text = valutes[i].name
//        checkBox.setOnCheckedChangeListener { _, _ ->
//            checkBox.isChecked = checkBox.isChecked
//        }
        linearLayout.setOnClickListener { onItemValuteClick(valutes[i], i) }
        return v
    }
}