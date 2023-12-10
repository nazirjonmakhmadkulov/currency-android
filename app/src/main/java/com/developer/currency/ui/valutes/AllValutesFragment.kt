package com.developer.currency.ui.valutes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.R
import com.developer.currency.core.base.BaseAdapter
import com.developer.currency.core.base.Item
import com.developer.currency.core.common.PATH_EXP
import com.developer.currency.core.utils.Utils
import com.developer.currency.core.utils.launchAndCollectIn
import com.developer.currency.databinding.FragmentAllValutesBinding
import com.developer.currency.domain.entities.Valute
import com.developer.currency.ui.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllValutesFragment : Fragment(R.layout.fragment_all_valutes) {
    private val viewBinding by viewBinding(FragmentAllValutesBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    private var valutes: MutableList<Valute> = mutableListOf()
    private val valuteList: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) {
        MutableList(valutes.size) { valutes[it] }
    }
    private val valCursAdapter: BaseAdapter = BaseAdapter(listOf(ValCursAdapter(::onItemValute)))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh()
        setupViews()
        setupViewModel()
    }

    private fun swipeRefresh() = with(viewBinding) {
        swipe.setColorSchemeResources(R.color.black_second)
        swipe.setOnRefreshListener {
            viewModel.getRemoteValutes(Utils.getDate(), PATH_EXP)
            swipe.isRefreshing = false
        }
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = valCursAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.getLocalValutes().launchAndCollectIn(viewLifecycleOwner) { getAllValuteSuccess(it) }
        viewModel.getRemoteValutes.launchAndCollectIn(viewLifecycleOwner) {}
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        this.valutes = valutes.toMutableList()
        valCursAdapter.submitList(valuteList)
    }

    private fun onItemValute(item: Valute) {
        val action = AllValutesFragmentDirections.openChartFragment(item.valId, item.charCode)
        findNavController().navigate(action)
    }
}