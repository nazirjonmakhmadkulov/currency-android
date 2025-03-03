package com.developer.favorite.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.common.FAVORITE_CONVERTER
import com.developer.common.FAVORITE_VALUTE
import com.developer.designsystem.base.BaseAdapter
import com.developer.designsystem.launchAndCollectIn
import com.developer.domain.model.Currency
import com.developer.favorite.R
import com.developer.favorite.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private val viewBinding by viewBinding(FragmentFavoritesBinding::bind)
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var sortAdapter: BaseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favorite = arguments?.getString("favorite")
        sortAdapter = BaseAdapter(listOf(FavoriteAdapter(favorite, ::onItemCurrency)))
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = sortAdapter
        }
    }

    private fun setupViewModel() {
        val favorite = arguments?.getString("favorite")
        if (FAVORITE_VALUTE == favorite) {
            viewModel.fetchFavorites().launchAndCollectIn(viewLifecycleOwner) { setCurrencies(it) }
        } else if (FAVORITE_CONVERTER == favorite) {
            viewModel.fetchConverters().launchAndCollectIn(viewLifecycleOwner) { setCurrencies(it) }
        }
    }

    private fun setCurrencies(currencies: List<Currency>) {
        sortAdapter.submitList(currencies)
    }

    private fun onItemCurrency(item: Currency, position: Int) {
        viewModel.updateLocalCurrency(item)
        sortAdapter.notifyItemChanged(position)
    }
}