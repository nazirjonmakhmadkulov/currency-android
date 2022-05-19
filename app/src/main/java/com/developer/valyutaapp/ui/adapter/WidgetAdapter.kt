package com.developer.valyutaapp.ui.adapter

import android.content.Context
import com.developer.valyutaapp.domain.entities.Valute
import android.view.ViewGroup
import android.view.LayoutInflater
import com.developer.valyutaapp.R
import android.view.View
import android.widget.*
import com.developer.valyutaapp.utils.ImageResource
import java.util.ArrayList

class WidgetAdapter(var context: Context, var valutes: ArrayList<Valute>) : BaseAdapter() {

    private var clickListener: ClickListener? = null

    override fun getCount(): Int {
        return valutes.size
    }

    override fun getItem(i: Int): Any {
        return valutes[i]
    }

    override fun getItemId(i: Int): Long {
        return valutes[i].id.toLong()
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.sort_item, viewGroup, false)
        val linearLayout = v.findViewById<View>(R.id.item_dialog) as LinearLayout
        val listView = v.findViewById<View>(R.id.list_dialog) as ListView
        val icon = v.findViewById<View>(R.id.img_flag) as ImageView
        val checkBox = v.findViewById<View>(R.id.checkSort) as CheckBox
        val tvName = v.findViewById<View>(R.id.name_currency) as TextView
        val bt = ImageResource.getImageRes(context, valutes[i].charCode.toString())
        icon.setImageBitmap(bt)
        tvName.text = valutes[i].name.toString()
        checkBox.setOnCheckedChangeListener { compoundButton, b ->
            checkBox.isChecked = checkBox.isChecked
        }
        linearLayout.setOnClickListener { clickListener!!.itemClicked(valutes[i], i) }
        return v
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    interface ClickListener {
        fun itemClicked(item: Valute?, position: Int)
    }
}