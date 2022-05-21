package com.developer.valyutaapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.developer.valyutaapp.domain.entities.Valute
import android.view.ViewGroup
import android.view.LayoutInflater
import com.developer.valyutaapp.R
import android.view.View
import android.widget.*
import com.developer.valyutaapp.utils.ImageResource

class WidgetAdapter(
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
        val v = inflater.inflate(R.layout.favorites_item, viewGroup, false)
        val linearLayout = v.findViewById<View>(R.id.item_dialog) as LinearLayout
        //val listView = v.findViewById<View>(R.id.list_dialog) as ListView
        val icon = v.findViewById<View>(R.id.img_flag) as ImageView
        val checkBox = v.findViewById<View>(R.id.favorite) as ImageView
        val tvName = v.findViewById<View>(R.id.name_currency) as TextView
        val bt = ImageResource.getImageRes(context, valutes[i].charCode)
        icon.setImageBitmap(bt)
        tvName.text = valutes[i].name
//        checkBox.setOnCheckedChangeListener { _, _ ->
//            checkBox.isChecked = checkBox.isChecked
//        }
        linearLayout.setOnClickListener {
            onItemValuteClick(valutes[i], i)
        }
        return v
    }
}