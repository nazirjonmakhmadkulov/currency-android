package com.developer.valyutaapp.ui.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseAdapter
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.core.common.FAVORITE_CONVERTER
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentConverterBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.ConAdapter
import com.developer.valyutaapp.ui.adapter.ConverterAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private val viewBinding by viewBinding(FragmentConverterBinding::bind)

    private val viewModel by viewModel<MainViewModel>()
    private val prefs: SharedPreference by inject()

    private var mathValutes: MutableList<Valute> = mutableListOf()
    private var valutes: MutableList<Valute> = mutableListOf()

    private val valuteList: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) {
        MutableList(valutes.size) { valutes[it] }
    }
    private val converterAdapter: BaseAdapter =
        BaseAdapter(listOf(ConverterAdapter(::onChangeValute, ::onItemValute)))

    private val conAdapter by lazy { ConAdapter(::onChangeValute, ::onItemValute) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViews()
        setupViewModel()
    }

    private fun setupToolbar() {
        with(viewBinding) {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit_favorites -> callFavoriteEdit()
                }
                return@setOnMenuItemClickListener true
            }
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
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getAllConverterLocalValutes().distinctUntilChanged().collectLatest {
                getAllValuteSuccess(it)
            }
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        this.valutes.addAll(valutes)
        //mathValutes.addAll(valutes)
        converterAdapter.submitList(valutes.toList())
    }

    private fun onItemValute(item: Valute) {

    }

    private fun onChangeValute(item: String, position: Int) {
        mathValutes.clear()
        valutes.forEachIndexed { index, valute ->
            if (index == position) {
                mathValutes.add(
                    Valute(
                        id = valute.id,
                        valId = valute.valId,
                        charCode = valute.charCode,
                        nominal = valute.nominal,
                        name = valute.name,
                        value = item,
                        dates = valute.dates,
                        favoritesValute = valute.favoritesValute,
                        favoritesConverter = valute.favoritesConverter,
                    )
                )
            } else {
                mathValutes.add(
                    Valute(
                        id = valute.id,
                        valId = valute.valId,
                        charCode = valute.charCode,
                        nominal = valute.nominal,
                        name = valute.name,
                        value = (valute.value.toDouble() * item.toDouble()).toString(),
                        dates = valute.dates,
                        favoritesValute = valute.favoritesValute,
                        favoritesConverter = valute.favoritesConverter,
                    )
                )
            }
//             println("${mathValutes[index]}")
//            if (position != index) {
//                converterAdapter.notifyItemChanged(index, mathValutes[index])
//                converterAdapter.notifyDataSetChanged()
//            }
             converterAdapter.submitList(mathValutes.toList())
            val sum = valute.value.toDouble() * item.toDouble()
            println("$sum")
        }
    }
}