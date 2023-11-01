package com.developer.currency.ui.converter

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.R
import com.developer.currency.core.base.BaseAdapter
import com.developer.currency.core.common.FAVORITE_CONVERTER
import com.developer.currency.databinding.FragmentConverterBinding
import com.developer.currency.domain.entities.Valute
import com.developer.currency.ui.adapter.ConverterAdapter
import com.developer.currency.utils.launchAndCollectIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private val viewBinding by viewBinding(FragmentConverterBinding::bind)
    private val viewModel by viewModel<ConverterViewModel>()
    private var valutes: MutableList<Valute> = mutableListOf()
    private val converterAdapter: BaseAdapter = BaseAdapter(listOf(ConverterAdapter()))
//    private val conAdapter by lazy { ConAdapter(::onChangeValute, ::onItemValute) }

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
        val action =
            ConverterFragmentDirections.actionNavigationConverterToEditFragment(FAVORITE_CONVERTER)
        findNavController().navigate(action)
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = converterAdapter
        }
        viewBinding.convert.moneyConvert.doOnTextChanged { text, _, _, _ ->
            try {
                if (!text.isNullOrEmpty()) {
                    viewModel.submitConverterInput(text.toString().toDouble())
                } else {
                    viewModel.submitConverterInput(0.0)
                }
            } catch (e: NumberFormatException) {
                Timber.e("NumberFormatException $e")
            }
        }
    }

    private fun setupViewModel() {
        viewModel.getAllConverterLocalValutes().launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            getAllValuteSuccess(it)
        }
        viewModel.valuteState.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) { items ->
            launch { delay(50); converterAdapter.submitList(items.toList()) }
        }
    }

    private fun getAllValuteSuccess(valute: List<Valute>) {
        this.valutes.clear()
        valute.forEach { it.value = "0"; this.valutes.add(it) }
        converterAdapter.submitList(valutes.toList())
    }
}