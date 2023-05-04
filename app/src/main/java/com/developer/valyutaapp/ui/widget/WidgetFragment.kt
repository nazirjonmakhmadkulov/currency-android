package com.developer.valyutaapp.ui.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseAdapter
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.databinding.FragmentWidgetBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.adapter.ValCursAdapter
import com.developer.valyutaapp.utils.launchAndCollectIn
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class WidgetFragment : Fragment(R.layout.fragment_widget) {
    private val viewBinding by viewBinding(FragmentWidgetBinding::bind)
    private val viewModel by viewModel<WidgetViewModel>()

    private var valutes: MutableList<Valute> = mutableListOf()
    private val valuteList: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) {
        MutableList(valutes.size) { valutes[it] }
    }
    private val valCursAdapter: BaseAdapter = BaseAdapter(listOf(ValCursAdapter(::onItemValute)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("listval ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewModel()
        with(viewBinding) {
            this.iconValute1.setOnClickListener {
                dialogValutes()
            }
        }
    }

    private fun setupToolbar() = with(viewBinding) {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun setupViewModel() {
        Timber.d("listval $valuteList")
        viewModel.getLocalValuteById(840)
        viewModel.getLocalValutes().launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            getAllValuteSuccess(it)
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        Timber.d("list == $valuteList")
        this.valutes = valutes.toMutableList()
    }

    private fun dialogValutes() {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        materialAlertDialogBuilder.setTitle("Все валюты")
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.row_item, null, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_dialog)
        Timber.d("listval $valuteList")
        valCursAdapter.submitList(valuteList)
        recyclerView.adapter = valCursAdapter
        materialAlertDialogBuilder.setView(view).show()
    }

    private fun onItemValute(item: Valute) {

    }
}