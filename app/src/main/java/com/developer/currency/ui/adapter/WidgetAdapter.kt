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
import com.developer.designsystem.icons.getImageRes
import com.developer.domain.model.Currency

class WidgetAdapter(
    private val context: Context,
    private val currencies: MutableList<Currency>,
    private val onItemValuteClick: (Currency, Int) -> Unit,
) : BaseAdapter() {
    override fun getCount(): Int = currencies.size

    override fun getItem(i: Int): Any = currencies[i]

    override fun getItemId(i: Int): Long = currencies[i].id.toLong()

    @SuppressLint("MissingInflatedId", "ViewHolder")
    override fun getView(
        i: Int,
        view: View,
        viewGroup: ViewGroup,
    ): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.favorites_item, viewGroup, false)
        val linearLayout = v.findViewById<View>(R.id.item_dialog) as LinearLayout
        // val listView = v.findViewById<View>(R.id.list_dialog) as ListView
        val icon = v.findViewById<View>(R.id.img_flag) as ImageView
        val tvName = v.findViewById<View>(R.id.name_currency) as TextView
        val drawable = context.getImageRes(currencies[i].charCode)
        icon.setImageDrawable(drawable)
        tvName.text = currencies[i].name
//        checkBox.setOnCheckedChangeListener { _, _ ->
//            checkBox.isChecked = checkBox.isChecked
//        }
        linearLayout.setOnClickListener { onItemValuteClick(currencies[i], i) }
        return v
    }
}