package com.developer.valyutaapp.ui.valute

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.developer.valyutaapp.domain.entities.Valute
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.util.Log
import com.developer.valyutaapp.utils.ImageResource
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.utils.Utils
import com.developer.valyutaapp.databinding.ActivityValuteBinding
import java.util.ArrayList

class ValuteActivity : AppCompatActivity(R.layout.activity_valute), ValuteViewInterface {

    private val viewBinding by viewBinding(ActivityValuteBinding::bind, R.id.container)

    var dialog: AlertDialog? = null
    private var valuteId: Long = 0

    private var valutes: MutableList<Valute> = ArrayList()
    private val adapter by lazy {  }

    //LineChart chart;
    //private var valutePresenter: ValutePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            valuteId = extras.getInt("id").toLong()
        }
        valutes = ArrayList()
        setupMVP()
        valuteById
        valuteList
        viewBinding.clear.setOnClickListener {
            viewBinding.edit1.setText("")
            viewBinding.edit2.setText("")
        }
        viewBinding.change.setOnClickListener {
            if (viewBinding.edit1.isEnabled && hasWindowFocus()) {
                viewBinding.edit2.requestFocus()
            }
            if (!viewBinding.edit1.isEnabled && !hasWindowFocus()) {
                viewBinding.edit1.requestFocus()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewBinding.edit1.isEnabled) {
            viewBinding.edit1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    if (editable.isEmpty()) {
                        return
                    } else if (viewBinding.name1.text == "TJS") {
                        nominal = viewBinding.name1.text.toString().toDouble()
                        val sum = Utils.mathNominal(nominal, value)
                        Log.d("sum", "$nominal*$value = $sum")
                        viewBinding.edit2.setText(sum.toString())
                    }
                }
            })
        } else {
            viewBinding.edit2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    if (editable.isEmpty()) {
                        return
                    } else if (viewBinding.name2.text == "TJS") {
                        val valueDynamic: Double = viewBinding.name2.text.toString().toDouble()
                        val sum = Utils.mathValue(nominal, value, valueDynamic)
                        Log.d("sum", "$nominal*$value = $sum")
                        viewBinding.edit1.setText(sum.toString())
                    }
                }
            })
        }
    }

    private fun setupMVP() {
        //valutePresenter = ValutePresenter(this.applicationContext, this)
    }

    private val valuteById: Unit
        get() {
           // valutePresenter!!.getValuteById(valuteId.toInt())
        }
    private val valuteList: Unit
        get() {
            //valutePresenter!!.valutes()
        }

    override fun showToast(s: String) {
        Toast.makeText(this@ValuteActivity, s, Toast.LENGTH_LONG).show()
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
        viewBinding.iconValute1.setImageBitmap(bt)
        value = valute.value.toDouble()
        viewBinding.edit1.setText(java.lang.String.valueOf(valute.nominal))
        viewBinding.edit2.setText(valute.value)
        viewBinding.name1.text = valute.charCode
        viewBinding.name2.text = "TJS"
        viewBinding.iconValute2.setImageResource(R.drawable.tajikistan)
    }

    override fun displayValutes(valute: List<Valute>) {
        valutes.addAll(valute)
    }

    override fun displayError(s: String) {
        showToast(s)
    }

    fun dialogValutes(v: View?) {
       val builder = AlertDialog.Builder(this)
        builder.setTitle("Все валюты")
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.row_item, null)
        val listView = row.findViewById<View>(R.id.list_dialog) as ListView
        //adapter = DialogAdapter(this, valutes!!)
       // listView.adapter = adapter
        builder.setView(row)
        dialog = builder.create()
        dialog!!.show()
    }

     fun itemClicked(item: Valute?, position: Int) {
        valuteId = item!!.id.toLong()
        dialog!!.hide()
        valuteById
    }

    companion object {
        var nominal = 0.0
        var value = 0.0
    }
}