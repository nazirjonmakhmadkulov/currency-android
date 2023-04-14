package com.developer.valyutaapp.ui.valutes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseAdapter
import com.developer.valyutaapp.core.base.Item
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.databinding.FragmentAllValutesBinding
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.ValCursAdapter
import com.developer.valyutaapp.utils.Utils
import com.developer.valyutaapp.utils.launchAndCollectIn
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

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
        swipe.setOnRefreshListener { viewModel.getRemoteValutes(Utils.getDate(), PATH_EXP) }
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = valCursAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.getLocalValutes().launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            getAllValuteSuccess(it)
        }
        viewModel.getRemoteValutes.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            subscribeValuteState(it)
        }
    }

    private fun subscribeValuteState(result: Result<ValCurs>) {
        when (result) {
            is Result.Loading -> {}
            is Result.Success -> viewBinding.swipe.isRefreshing = false
            is Result.Error -> {
                viewBinding.swipe.isRefreshing = false
                Timber.d("Error ", result.code.toString() + " = " + result.errorMessage)
            }
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        this.valutes = valutes.toMutableList()
        valCursAdapter.submitList(valuteList)
    }

    private fun onItemValute(item: Valute) {
        val action =
            AllValutesFragmentDirections.actionNavigationValutesToChartFragment(item.valId, item.charCode)
        findNavController().navigate(action)
    }
}