package com.developer.valyutaapp.ui.valute

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.databinding.FragmentValuteBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.adapter.DialogAdapter
import com.developer.valyutaapp.utils.ImageResource
import com.developer.valyutaapp.utils.Utils
import java.util.ArrayList

class ValuteFragment : Fragment(R.layout.fragment_valute) {

    private val viewBinding by viewBinding(FragmentValuteBinding::bind)

    var dialog: AlertDialog? = null
    private var valuteId: Long = 0
    private var valutes: MutableList<Valute> = ArrayList()
    private val adapter by lazy { DialogAdapter(requireContext(), valutes, ::onItemValute) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val extras = ite.extras
//        if (extras != null) {
//            valuteId = extras.getInt("id").toLong()
//        }

        setupMVP()
        viewBinding.clear.setOnClickListener {
            viewBinding.edit1.setText("")
            viewBinding.edit2.setText("")
        }
        viewBinding.change.setOnClickListener {
            if (viewBinding.edit1.isEnabled && requireActivity().hasWindowFocus()) {
                viewBinding.edit2.requestFocus()
            }
            if (!viewBinding.edit1.isEnabled && !requireActivity().hasWindowFocus()) {
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
                        ValuteActivity.nominal = viewBinding.name1.text.toString().toDouble()
                        val sum = Utils.mathNominal(ValuteActivity.nominal, ValuteActivity.value)
                        Log.d("sum", "${ValuteActivity.nominal}*${ValuteActivity.value} = $sum")
                        viewBinding.edit2.setText(sum.toString())
                    }
                }
            })
        } else {
            viewBinding.edit2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence, i: Int,
                    i1: Int, i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    if (editable.isEmpty()) {
                        return
                    } else if (viewBinding.name2.text == "TJS") {
                        val valueDynamic: Double = viewBinding.name2.text.toString().toDouble()
                        val sum = Utils.mathValue(
                            ValuteActivity.nominal,
                            ValuteActivity.value, valueDynamic
                        )
                        Log.d("sum", "${ValuteActivity.nominal}*${ValuteActivity.value} = $sum")
                        viewBinding.edit1.setText(sum.toString())
                    }
                }
            })
        }
    }

    private fun setupMVP() {
        //valutePresenter = ValutePresenter(this.applicationContext, this)
    }

    @SuppressLint("SetTextI18n")
    fun displayValuteWithId(valute: Valute) {
        val bt = ImageResource.getImageRes(requireContext(), valute.charCode)
        viewBinding.iconValute1.setImageBitmap(bt)
        value = valute.value.toDouble()
        viewBinding.edit1.setText(java.lang.String.valueOf(valute.nominal))
        viewBinding.edit2.setText(valute.value)
        viewBinding.name1.text = valute.charCode
        viewBinding.name2.text = "TJS"
        viewBinding.iconValute2.setImageResource(R.drawable.tajikistan)
    }


    fun dialogValutes(v: View?) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Все валюты")
        val inflater =
            requireActivity().getSystemService(AppCompatActivity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.row_item, null)
        val listView = row.findViewById<View>(R.id.list_dialog) as ListView
        listView.adapter = adapter
        builder.setView(row)
        dialog = builder.create()
        dialog!!.show()
    }

    private fun onItemValute(item: Valute?, position: Int) {
        valuteId = item!!.id.toLong()
        dialog!!.hide()
        //valuteById
    }

    companion object {
        var nominal = 0.0
        var value = 0.0
    }

}