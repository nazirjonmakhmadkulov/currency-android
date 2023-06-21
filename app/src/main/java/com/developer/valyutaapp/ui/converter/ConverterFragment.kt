package com.developer.valyutaapp.ui.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseAdapter
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.core.common.FAVORITE_CONVERTER
import com.developer.valyutaapp.databinding.FragmentConverterBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.adapter.ConverterAdapter
import com.developer.valyutaapp.utils.launchAndCollectIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ConverterFragment : Fragment(R.layout.fragment_converter) {
    private val viewBinding by viewBinding(FragmentConverterBinding::bind)
    private val viewModel by viewModel<ConverterViewModel>()
    private var valutes: MutableList<Valute> = mutableListOf()
    private val valuteList: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) {
        MutableList(valutes.size) { valutes[it] }
    }
    private val converterAdapter: BaseAdapter = BaseAdapter(listOf(ConverterAdapter(::onChangeValute, ::onItemValute)))
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

        viewBinding.convert.moneyConvert.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotBlank()) {
                    viewModel.submitSearchInput(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupViewModel() {
        viewModel.getAllConverterLocalValutes().launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            getAllValuteSuccess(it)
        }
        viewModel.valuteState.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            //it?.let { items -> getAllValuteSuccess(items) }
            Timber.d("valuteState ${it}")
        }
    }

    private fun getAllValuteSuccess(valute: List<Valute>) {
        this.valutes.clear()
        this.valutes.addAll(valute)
        converterAdapter.submitList(valutes.toList())
    }

    private fun onItemValute(item: Valute) {}
    private fun onChangeValute(item: String, position: Int) {}
}