package com.developer.valyutaapp.ui.widget

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.databinding.ActivityWidgetBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.adapter.DialogAdapter
import com.developer.valyutaapp.utils.ImageResource
import com.developer.valyutaapp.utils.Utils
import io.paperdb.Paper
import java.text.DecimalFormat

class WidgetActivity : AppCompatActivity(R.layout.activity_widget), WidgetViewInterface {
    private val viewBinding by viewBinding(ActivityWidgetBinding::bind, R.id.container)

    private var alertdialog: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null
    private var valutes: ArrayList<Valute>? = null
    private var valuteId = 840
    private var adapter: DialogAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        valutes = ArrayList()
        setupMVP()
        valuteById
        valuteList
        viewBinding.saveWidget.setOnClickListener {
            val decimalFormat = DecimalFormat("#.####")
            val decimal = decimalFormat.format(viewBinding.tvValue.text.toString().toDouble())
            val value = if (viewBinding.tvNominal.text.toString().length < 3) {
                decimalFormat.format(viewBinding.tvNominal.text.toString().toDouble())
            } else {
                viewBinding.tvNominal.text.toString()
            }
            Paper.init(this)
            Paper.book().write("charcode", viewBinding.name1.text)
            Paper.book().write("charcode2", viewBinding.name2.text)
            Paper.book().write("nominal", value)
            Paper.book().write("value", decimal.toString())
            Paper.book().write("dat", Utils.getDate())
            Toast.makeText(this, "Через минуту он будет установлен", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupMVP() {
        //widgetPresenter = WidgetPresenter(this.applicationContext, this)
    }

    private val valuteById: Unit
        get() {
            //widgetPresenter!!.getValuteById(valuteId)
        }
    private val valuteList: Unit
        get() {
            // widgetPresenter!!.valutes()
        }

    override fun showToast(s: String) {
        Toast.makeText(this@WidgetActivity, s, Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar() {
        viewBinding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        viewBinding.progressBar.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun displayValuteWithId(valute: Valute) {
        val bt = ImageResource.getImageRes(this, valute.charCode)
        viewBinding.iconValute1.setImageDrawable(bt)
        viewBinding.tvNominal.text = java.lang.String.valueOf(valute.nominal)
        viewBinding.tvValue.text = valute.value
        viewBinding.name1.text = valute.charCode
        viewBinding.name2.text = "TJS"
        viewBinding.iconValute2.setImageResource(R.drawable.tajikistan)
    }

    override fun displayValutes(valute: List<Valute>) {
        valutes!!.addAll(valute)
    }

    override fun displayError(s: String) {
        showToast(s)
    }

    fun dialogValutes() {
        alertdialog = AlertDialog.Builder(this)
        alertdialog!!.setTitle("Все валюты")
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.row_item, null)
        val listView = row.findViewById<View>(R.id.list_dialog) as ListView
        //adapter = DialogAdapter(this, valutes!!)
        listView.adapter = adapter
        alertdialog!!.setView(row)
        dialog = alertdialog!!.create()
        dialog!!.show()
    }
}