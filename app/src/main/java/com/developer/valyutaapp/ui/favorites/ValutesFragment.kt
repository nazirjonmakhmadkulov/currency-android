package com.developer.valyutaapp.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.base.BaseAdapter
import com.developer.valyutaapp.databinding.FragmentValutesBinding
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.FavoriteAdapter
import com.developer.valyutaapp.utils.launchAndCollectIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class ValutesFragment : Fragment(R.layout.fragment_valutes) {
    private val viewBinding by viewBinding(FragmentValutesBinding::bind)
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var sortAdapter: BaseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favorite = arguments?.getString("favorite")
        sortAdapter = BaseAdapter(listOf(FavoriteAdapter(favorite, ::onItemValute)))
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
        viewModel.getLocalValutes().launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            getAllValuteSuccess(it)
        }
    }

    private fun getAllValuteSuccess(valutes: List<Valute>) {
        sortAdapter.submitList(valutes)
    }

    private fun onItemValute(item: Valute, position: Int) {
        viewModel.updateLocalValute(item)
        sortAdapter.notifyItemChanged(position)
    }
}
