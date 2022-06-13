package com.developer.valyutaapp.ui.valutes

import android.os.Bundle
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
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentAllValutesBinding
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.ValCursAdapter
import com.developer.valyutaapp.utils.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllValutesFragment : Fragment(R.layout.fragment_all_valutes) {

    private val viewBinding by viewBinding(FragmentAllValutesBinding::bind)

    private val viewModel by viewModel<MainViewModel>()
    private val prefs: SharedPreference by inject()

    private var valutes: MutableList<Valute> = mutableListOf()
    private val valuteList: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) {
        MutableList(valutes.size) { valutes[it] }
    }
    private val valCursAdapter: BaseAdapter =
        BaseAdapter(listOf(ValCursAdapter(::onItemValute)))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh()
        setupViews()
        setupViewModel()
    }

    private fun swipeRefresh() = with(viewBinding) {
        swipe.setColorSchemeResources(
            R.color.black_second
        )
        swipe.setOnRefreshListener {
            viewModel.getRemoteValutes(Utils.getDate(), PATH_EXP)
        }
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
        viewModel.getRemoteValutes.observe(viewLifecycleOwner) {
            subscribeValuteState(it)
        }
    }

    private fun subscribeValuteState(it: Result<ValCurs>) {
        when (it) {
            is Result.Loading -> {}
            is Result.Success -> {
                viewBinding.swipe.isRefreshing = false
            }
            is Result.Error -> {
                viewBinding.swipe.isRefreshing = false
                Log.d("Error ", it.code.toString() + " == " + it.errorMessage)
            }
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        this.valutes = valutes.toMutableList()
        valCursAdapter.submitList(valuteList)
    }

    private fun onItemValute(item: Valute) {
        viewModel.getRemoteHistories(Utils.getMonthAge(), Utils.getDate(), item.valId, item.charCode, PATH_EXP)
        val action = AllValutesFragmentDirections.actionNavigationValutesToChartFragment(item.valId)
        findNavController().navigate(action)
    }
}