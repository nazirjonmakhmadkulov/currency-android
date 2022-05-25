package com.developer.valyutaapp.ui.valutes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseAdapter
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentAllValutesBinding
import com.developer.valyutaapp.databinding.FragmentConverterBinding
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.ValCursAdapter
import com.developer.valyutaapp.ui.home.HomeFragmentDirections
import com.developer.valyutaapp.ui.valute.ValuteActivity
import com.developer.valyutaapp.utils.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class AllValutesFragment : Fragment(R.layout.fragment_all_valutes) {

    private val viewBinding by viewBinding(FragmentAllValutesBinding::bind)

    private val viewModel by viewModel<MainViewModel>()
    private val prefs: SharedPreference by inject()

    private var valutes: MutableList<Valute> = mutableListOf()
    private val valuteList: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) {
        MutableList(valutes.size) { valutes[it] }
    }
    private val valCursAdapter: BaseAdapter =
        BaseAdapter(listOf(ValCursAdapter(requireContext(), ::onItemValute)))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = valCursAdapter
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getLocalValutes().distinctUntilChanged().collectLatest {
                getAllValuteSuccess(it)
            }
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        this.valutes = valutes.toMutableList()
        valCursAdapter.submitList(valuteList)
    }

    private fun onItemValute(item: Valute) {
        val action = AllValutesFragmentDirections.actionNavigationValutesToChartFragment(item.id)
        findNavController().navigate(action)
    }
}