package com.developer.converter

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.common.FAVORITE_CONVERTER
import com.developer.converter.databinding.FragmentConverterBinding
import com.developer.designsystem.base.BaseAdapter
import com.developer.designsystem.launchAndCollectIn
import com.developer.domain.model.Currency
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private val viewBinding by viewBinding(FragmentConverterBinding::bind)
    private val viewModel: ConverterViewModel by viewModels()
    private var currencies: MutableList<Currency> = mutableListOf()
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
        val action = ConverterFragmentDirections.openPagerFragment(FAVORITE_CONVERTER)
        findNavController().navigate(action)
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = converterAdapter
        }
        viewBinding.convert.moneyConvert.doOnTextChanged { text, _, _, _ ->
            try {
                if (text.isNullOrEmpty()) viewModel.submitConverterInput(0, 0.0, 1, 0.0)
                else viewModel.submitConverterInput(0, text.toString().toDouble(), 1, 0.0)
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
        viewModel.getConverterLocalCurrencies().launchAndCollectIn(viewLifecycleOwner) { setCurrencies(it) }
        viewModel.foreignCurrencyState.launchAndCollectIn(viewLifecycleOwner) { items ->
            converterAdapter.submitList(items.toList())
        }
        viewModel.nationalCurrencyState.launchAndCollectIn(viewLifecycleOwner) { items ->
            viewBinding.convert.moneyConvert.hint = items
        }
    }

    private fun setCurrencies(currency: List<Currency>) {
        this.currencies.clear()
        this.currencies.addAll(currency)
        converterAdapter.submitList(currencies.toList())
        viewBinding.convert.cardId.isVisible = currencies.isNotEmpty()
    }

    private fun onItemChange(id: Int, amount: String, nominal: Int, value: String) {
        if (amount.isEmpty()) viewModel.submitConverterInput(id, 0.0, 0, 0.0)
        else viewModel.submitConverterInput(id, amount.toDouble(), nominal, value.toDouble())
    }
}