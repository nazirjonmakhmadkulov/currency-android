package com.developer.valyutaapp.ui.favorite_edit


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseAdapter
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentValutesBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.FavoriteAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ValutesFragment : Fragment(R.layout.fragment_valutes) {

    private val viewBinding by viewBinding(FragmentValutesBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    private val prefs: SharedPreference by inject()

    private lateinit var sortAdapter: BaseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favorite = arguments?.getString("favorite")
        sortAdapter =  BaseAdapter(listOf(FavoriteAdapter(requireContext(), favorite, ::onItemValute)))
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
            viewModel.getLocalValutes().distinctUntilChanged().collectLatest {
                getAllValuteSuccess(it)
            }
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        sortAdapter.submitList(valutes)
    }

    private fun onItemValute(item: Valute) {
        viewModel.updateLocalValute(item)
    }
}