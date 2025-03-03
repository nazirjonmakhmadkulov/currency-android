package com.developer.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.common.FAVORITE_VALUTE
import com.developer.designsystem.base.BaseAdapter
import com.developer.designsystem.launchAndCollectIn
import com.developer.domain.model.Currency
import com.developer.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private val valutesAdapter: BaseAdapter = BaseAdapter(listOf(HomeAdapter(::onItemCurrency)))

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
            swipe.isRefreshing = false
        }
    }

    private fun callFavoriteEdit() {
        val action = HomeFragmentDirections.openPagerFragment(FAVORITE_VALUTE)
        findNavController().navigate(action)
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = valutesAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.fetchFavoriteCurrencies().launchAndCollectIn(viewLifecycleOwner) { setCurrencies(it) }
    }

    private fun setCurrencies(currencies: List<Currency>) {
        valutesAdapter.submitList(currencies)
    }

    private fun onItemCurrency(item: Currency) {
        val action = HomeFragmentDirections.openChartFragment(item.valId, item.charCode)
        findNavController().navigate(action)
    }
}