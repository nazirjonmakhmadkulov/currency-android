package com.developer.currency.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.R
import com.developer.currency.core.base.BaseAdapter
import com.developer.currency.core.common.FAVORITE_VALUTE
import com.developer.currency.core.common.PATH_EXP
import com.developer.currency.core.utils.Utils
import com.developer.currency.core.utils.launchAndCollectIn
import com.developer.currency.databinding.FragmentHomeBinding
import com.developer.currency.domain.entities.Valute
import com.developer.currency.ui.MainViewModel
import com.developer.currency.ui.valutes.ValCursAdapter
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
    private val mainViewModel by activityViewModel<MainViewModel>()
    private val viewModel by viewModel<HomeViewModel>()

    private val valutesAdapter: BaseAdapter = BaseAdapter(listOf(ValCursAdapter(::onItemValute)))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        swipeRefresh()
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

    private fun swipeRefresh() = with(viewBinding) {
        swipe.setColorSchemeResources(R.color.black_second)
        swipe.setOnRefreshListener {
            mainViewModel.getRemoteValutes(Utils.getDate(), PATH_EXP)
            swipe.isRefreshing = false
        }
    }

    private fun callFavoriteEdit() {
        val action = HomeFragmentDirections.openEditFragment(FAVORITE_VALUTE)
        findNavController().navigate(action)
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = valutesAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.fetchFavoriteValutes().launchAndCollectIn(viewLifecycleOwner) { setValutes(it) }
        mainViewModel.getRemoteValutes.launchAndCollectIn(viewLifecycleOwner) {}
    }

    private fun setValutes(valutes: List<Valute>) {
        valutesAdapter.submitList(valutes)
    }

    private fun onItemValute(item: Valute) {
        val action = HomeFragmentDirections.openChartFragment(item.valId, item.charCode)
        findNavController().navigate(action)
    }
}