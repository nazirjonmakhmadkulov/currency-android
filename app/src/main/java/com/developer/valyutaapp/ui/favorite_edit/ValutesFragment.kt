package com.developer.valyutaapp.ui.favorite_edit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.FAVORITE_CONVERTER
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentValutesBinding
import com.developer.valyutaapp.domain.entities.Favorite
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.FavoriteAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class ValutesFragment : Fragment(R.layout.fragment_valutes) {

    private val viewBinding by viewBinding(FragmentValutesBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    private val prefs: SharedPreference by inject()

    private var valuteList: MutableList<Valute> = ArrayList()
    private val sortAdapter by lazy {
        FavoriteAdapter(
            requireContext(),
            valuteList,
            ::onItemValute
        )
    }

    val favorite = arguments?.getString("favorite")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        lifecycleScope.launchWhenCreated {
            viewModel.getLocalValutes().collect {
                getAllValuteSuccess(it)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllValuteSuccess(valutes: List<Valute>) {
        valuteList.clear()
        valuteList.addAll(valutes)
        sortAdapter.notifyDataSetChanged()
    }

    private fun onItemValute(item: Valute, position: Int) {
        // viewModel.updateLocalValute(item)
        if (item.favoritesValute == 1) {
            viewModel.insertLocalFavorite(
                Favorite(
                    id = item.id,
                    favorite_converter = item.favoritesValute,
                    favorite_valute = item.favoritesValute
                )
            )
        } else if (item.favoritesValute == 1) {
            viewModel.deleteLocalFavorite(item.id)
        }
    }
}