package com.developer.currency.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.developer.currency.R
import com.developer.designsystem.icons.getImageRes
import com.developer.domain.model.Currency

class DialogAdapter(
    private val context: Context,
    private val currencies: MutableList<Currency>,
) : BaseAdapter() {
    override fun getCount(): Int = currencies.size

    override fun getItem(i: Int): Any = currencies[i]

    override fun getItemId(i: Int): Long = currencies[i].id.toLong()

    @Suppress("NAME_SHADOWING")
    @SuppressLint("ViewHolder")
    override fun getView(
        i: Int,
        view: View,
        viewGroup: ViewGroup,
    ): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_item, viewGroup, false)
        val icon = view.findViewById<ImageView>(R.id.img_flag)
        val txName = view.findViewById<TextView>(R.id.name_currency)
        val drawable = context.getImageRes(currencies[i].charCode)
        icon.setImageDrawable(drawable)
        txName.text = currencies[i].name
        return view
    }
}