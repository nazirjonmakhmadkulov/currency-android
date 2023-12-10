package com.developer.currency.ui.converter

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.R
import com.developer.currency.core.base.BaseAdapter
import com.developer.currency.core.common.FAVORITE_CONVERTER
import com.developer.currency.core.utils.launchAndCollectIn
import com.developer.currency.databinding.FragmentConverterBinding
import com.developer.currency.domain.entities.Valute
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private val viewBinding by viewBinding(FragmentConverterBinding::bind)
    private val viewModel by viewModel<ConverterViewModel>()
    private var valutes: MutableList<Valute> = mutableListOf()
    private val converterAdapter: BaseAdapter = BaseAdapter(listOf(ConverterAdapter(::onItemChange)))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViews()
        setupViewModel()
    }

    private fun setupToolbar() = with(viewBinding) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit_favorites -> callFavoriteEdit()
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun callFavoriteEdit() {
        val action = ConverterFragmentDirections.openEditFragment(FAVORITE_CONVERTER)
        findNavController().navigate(action)
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = converterAdapter
        }
        viewBinding.convert.moneyConvert.doOnTextChanged { text, _, _, _ ->
            try {
                if (text.isNullOrEmpty()) viewModel.submitConverterInput(0, 0.0, 0.0)
                else viewModel.submitConverterInput(0, text.toString().toDouble(), 0.0)
            } catch (e: NumberFormatException) {
                Timber.e("NumberFormatException $e")
            }
        }
        viewBinding.convert.moneyConvert.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewBinding.convert.moneyConvert.setText("")
                viewBinding.convert.moneyConvert.hint = "0.0"
            }
        }
    }

    private fun setupViewModel() {
        viewModel.getAllConverterLocalValutes().launchAndCollectIn(viewLifecycleOwner) { getAllValuteSuccess(it) }
        viewModel.foreignValuteState.launchAndCollectIn(viewLifecycleOwner) { items ->
            converterAdapter.submitList(items.toList())
        }
        viewModel.nationalValuteState.launchAndCollectIn(viewLifecycleOwner) { items ->
            viewBinding.convert.moneyConvert.hint = items
        }
    }

    private fun getAllValuteSuccess(valute: List<Valute>) {
        this.valutes.clear()
        this.valutes.addAll(valute)
        converterAdapter.submitList(valutes.toList())
    }

    private fun onItemChange(id: Int, query: String, value: String) {
        if (query.isEmpty()) viewModel.submitConverterInput(id, 0.0, 0.0)
        else viewModel.submitConverterInput(id, query.toDouble(), value.toDouble())
    }
}