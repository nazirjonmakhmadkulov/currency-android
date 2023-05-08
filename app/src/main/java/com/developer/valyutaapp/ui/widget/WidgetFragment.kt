package com.developer.valyutaapp.ui.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseAdapter
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.databinding.FragmentWidgetBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.adapter.ValDialogAdapter
import com.developer.valyutaapp.utils.Utils
import com.developer.valyutaapp.utils.launchAndCollectIn
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.paperdb.Paper
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

class WidgetFragment : Fragment(R.layout.fragment_widget) {
    private val viewBinding by viewBinding(FragmentWidgetBinding::bind)
    private val viewModel by viewModel<WidgetViewModel>()

    private var dialog: AlertDialog? = null

    private var valutes: MutableList<Valute> = mutableListOf()
    private val valuteList: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) {
        MutableList(valutes.size) { valutes[it] }
    }
    private val valCursAdapter: BaseAdapter = BaseAdapter(listOf(ValDialogAdapter(::onItemValute)))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewModel()
        viewBinding.iconValute1.setOnClickListener { dialogValutes() }
    }

    private fun setupToolbar() = with(viewBinding) {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun setupViewModel() {
        viewModel.getLocalValutes().launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) { valutes ->
            getAllValuteSuccess(valutes)
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        this.valutes.clear()
        this.valutes.addAll(valutes.toMutableList())
    }

    private fun setWidgetData() {
        val decimalFormat = DecimalFormat("#.####")
        val decimal = decimalFormat.format(viewBinding.tvValue.text.toString().toDouble())
        val value = if (viewBinding.tvNominal.text.toString().length < 3) {
            decimalFormat.format(viewBinding.tvNominal.text.toString().toDouble())
        } else {
            viewBinding.tvNominal.text.toString()
        }
        Paper.init(requireContext())
        Paper.book().write("charcode", viewBinding.name1.text)
        Paper.book().write("charcode2", viewBinding.name2.text)
        Paper.book().write("nominal", value)
        Paper.book().write("value", decimal.toString())
        Paper.book().write("dat", Utils.getDate())
    }

    private fun dialogValutes() {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        materialAlertDialogBuilder.setTitle(getString(R.string.all_valutes))
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog, null, false)
        view.findViewById<RecyclerView>(R.id.recycler_dialog).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = valCursAdapter
        }
        valCursAdapter.submitList(valuteList)
        materialAlertDialogBuilder.setView(view)
        dialog = materialAlertDialogBuilder.create()
        dialog?.show()
    }

    private fun onItemValute(item: Valute) {
        dialog?.dismiss()
    }
}