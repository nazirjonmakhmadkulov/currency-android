package com.developer.valyutaapp.ui.widget

import androidx.appcompat.app.AppCompatActivity
import com.developer.valyutaapp.model.Valute
import com.developer.valyutaapp.adapter.DialogAdapter
import butterknife.BindView
import com.developer.valyutaapp.R
import android.os.Bundle
import butterknife.ButterKnife
import android.util.Log
import com.developer.valyutaapp.utils.ImageResource
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.developer.valyutaapp.utils.Utils
import io.paperdb.Paper
import java.text.DecimalFormat
import java.util.ArrayList

class WidgetActivity : AppCompatActivity(), WidgetViewInterface, DialogAdapter.ClickListener {
    var alertdialog: AlertDialog.Builder? = null
    var dialog: AlertDialog? = null
    var valutes: ArrayList<Valute>? = null
    private var valuteId = 840
    var adapter: DialogAdapter? = null

    @BindView(R.id.iconValute1)
    var iconValute1: ImageView? = null

    @BindView(R.id.iconValute2)
    var iconValute2: ImageView? = null

    @BindView(R.id.tvNominal)
    var tvNominal: TextView? = null

    @BindView(R.id.tvValue)
    var tvValue: TextView? = null

    @BindView(R.id.name1)
    var code1: TextView? = null

    @BindView(R.id.name2)
    var code2: TextView? = null

    @BindView(R.id.saveWidget)
    var saveWidget: Button? = null

    @BindView(R.id.progressBar)
    var progressBar: ProgressBar? = null
    private var widgetPresenter: WidgetPresenter? = null
    private val TAG = "WidgetPresenter"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget)
        ButterKnife.bind(this)
        valutes = ArrayList()
        setupMVP()
        valuteById
        valuteList
        saveWidget!!.setOnClickListener {
            val val_dec: String
            val decimalFormat = DecimalFormat("#.####")
            val decimal = decimalFormat.format(tvValue!!.text.toString().toDouble())
            val_dec = if (tvNominal!!.text.toString().length < 3) {
                decimalFormat.format(tvNominal!!.text.toString().toDouble())
            } else {
                tvNominal!!.text.toString()
            }
            Paper.init(this@WidgetActivity)
            Paper.book().write("charcode", code1!!.text)
            Log.d("widget", " = " + code1!!.text)
            Paper.book().write("charcode2", code2!!.text)
            Log.d("widget", " = " + code2!!.text)
            Paper.book().write("nominal", val_dec)
            Log.d("widget", " = " + tvNominal!!.text)
            Paper.book().write("value", decimal.toString())
            Log.d("widget", " = $decimal")
            Paper.book().write("dat", Utils.date)
            Toast.makeText(
                this@WidgetActivity,
                "Через минуту он будет установлен",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupMVP() {
        widgetPresenter = WidgetPresenter(this.applicationContext, this)
    }

    private val valuteById: Unit
        private get() {
            widgetPresenter!!.getValuteById(valuteId)
        }
    private val valuteList: Unit
        private get() {
            widgetPresenter!!.valutes()
        }

    override fun showToast(str: String) {
        Toast.makeText(this@WidgetActivity, str, Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar!!.visibility = View.GONE
    }

    override fun displayValuteWithId(valute: Valute) {
        val bt = ImageResource.getImageRes(this, valute.charCode.toString())
        iconValute1!!.setImageBitmap(bt)
        tvNominal!!.text = java.lang.String.valueOf(valute.nominal)
        tvValue!!.text = valute.value.toString()
        code1!!.text = valute.charCode.toString()
        code2!!.text = "TJS"
        iconValute2!!.setImageResource(R.drawable.tajikistan)
    }

    override fun displayValutes(valute: List<Valute>) {
        valutes!!.addAll(valute)
    }

    override fun displayError(e: String) {
        showToast(e)
    }

    fun dialogValutes(v: View?) {
        alertdialog = AlertDialog.Builder(this)
        alertdialog!!.setTitle("Все валюты")
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.row_item, null)
        val listView = row.findViewById<View>(R.id.list_dialog) as ListView
        adapter = DialogAdapter(this, valutes!!)
        adapter!!.setClickListener(this@WidgetActivity)
        listView.adapter = adapter
        alertdialog!!.setView(row)
        dialog = alertdialog!!.create()
        dialog!!.show()
    }

    override fun itemClicked(item: Valute?, position: Int) {
        valuteId = item!!.id.toInt()
        dialog!!.hide()
        valuteById
    }
}