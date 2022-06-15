package com.developer.valyutaapp.ui.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentConverterBinding
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.ConAdapter
import com.developer.valyutaapp.ui.adapter.ConverterAdapter
import com.developer.valyutaapp.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
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

        viewBinding.convert.moneyConvert.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotBlank()) {
                    valutes.clear()
                    lifecycleScope.launch {
                        delay(100)
                        if (!viewBinding.convert.moneyConvert.text.isNullOrBlank()) {
                            val num = viewBinding.convert.moneyConvert.text.toString()
                            mathValutes.forEachIndexed { index, valute ->
                                valutes.add(
                                    Valute(
                                        id = valute.id,
                                        valId = valute.valId,
                                        charCode = valute.charCode,
                                        nominal = valute.nominal,
                                        name = valute.name,
                                        value = (valute.nominal.toDouble() * (num.toDouble() / valute.value.toDouble())).toString(),
                                        dates = valute.dates,
                                        favoritesValute = valute.favoritesValute,
                                        favoritesConverter = valute.favoritesConverter,
                                    )
                                )
                                //val sum = valute.value.toDouble() * item.toDouble()
                                //println("$sum")
                            }
                            converterAdapter.submitList(valutes.toList())
                            converterAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getAllConverterLocalValutes().distinctUntilChanged().collectLatest {
                getAllValuteSuccess(it)
            }
        }
    }

    private fun getAllValuteSuccess(valute: List<Valute>) {
        this.valutes.addAll(valute)
        mathValutes.addAll(valute)
        converterAdapter.submitList(valutes.toList())
    }

    private fun onItemValute(item: Valute) {

    }

    private fun onChangeValute(item: Double, position: Int) {
        valutes.clear()
        lifecycleScope.launch {
            delay(300)
            mathValutes.forEachIndexed { index, valute ->
                val valu = valute.value.trim().toDouble()
                println(item)
//                val ite = item.trim().toDouble()
//                val sum = valu * ite
//                valutes.add(
//                    Valute(
//                        id = valute.id,
//                        valId = valute.valId,
//                        charCode = valute.charCode,
//                        nominal = valute.nominal,
//                        name = valute.name,
//                        value = sum.toString(),
//                        dates = valute.dates,
//                        favoritesValute = valute.favoritesValute,
//                        favoritesConverter = valute.favoritesConverter,
//                    )
//                )
//            if (index == position) {
//                mathValutes.add(
//                    Valute(
//                        id = valute.id,
//                        valId = valute.valId,
//                        charCode = valute.charCode,
//                        nominal = valute.nominal,
//                        name = valute.name,
//                        value = item,
//                        dates = valute.dates,
//                        favoritesValute = valute.favoritesValute,
//                        favoritesConverter = valute.favoritesConverter,
//                    )
//                )
//            } else {
//
//            }
//                println("${valutes[index]}")
//                converterAdapter.notifyItemChanged(index, mathValutes[index])
//                converterAdapter.notifyDataSetChanged()

//                val sum = valute.value.toDouble() * item.toDouble()
//                println("$sum")
            }
            converterAdapter.submitList(valutes.toList())
            converterAdapter.notifyDataSetChanged()
        }
    }
}