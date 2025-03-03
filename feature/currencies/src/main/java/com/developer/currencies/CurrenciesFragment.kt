package com.developer.currencies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.common.Item
import com.developer.currencies.databinding.FragmentCurrenciesBinding
import com.developer.designsystem.base.BaseAdapter
import com.developer.designsystem.launchAndCollectIn
import com.developer.domain.model.Currency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment : Fragment(R.layout.fragment_currencies) {
    private val viewBinding by viewBinding(FragmentCurrenciesBinding::bind)
    private val viewModel: CurrenciesViewModel by viewModels()

    private var currencies: MutableList<Currency> = mutableListOf()
    private val valuteList: MutableList<Item> by lazy(LazyThreadSafetyMode.NONE) {
        MutableList(currencies.size) { currencies[it] }
    }
    private val currenciesAdapter: BaseAdapter = BaseAdapter(listOf(CurrenciesAdapter(::onItemCurrency)))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh()
        setupViews()
        setupViewModel()
    }

    private fun swipeRefresh() = with(viewBinding) {
        swipe.setColorSchemeResources(R.color.black_second)
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
        }
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = currenciesAdapter
        }
    }

    private fun setupViewModel() {
        viewModel.fetchLocalCurrencies().launchAndCollectIn(viewLifecycleOwner) { setCurrencies(it) }
    }

    private fun setCurrencies(currencies: List<Currency>) {
        this.currencies = currencies.toMutableList()
        currenciesAdapter.submitList(valuteList)
    }

    private fun onItemCurrency(item: Currency) {
        val action = CurrenciesFragmentDirections.openChartFragment(item.valId, item.charCode)
        findNavController().navigate(action)
    }
}