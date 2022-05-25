package com.developer.valyutaapp.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.FAVORITE_VALUTE
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentHomeBinding
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.adapter.ValCursAdapter
import com.developer.valyutaapp.ui.valute.ValuteActivity
import com.developer.valyutaapp.utils.Utils
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModel<MainViewModel>()
    private val prefs: SharedPreference by inject()

    var valuteList: MutableList<Valute> = ArrayList()

    private val valCursAdapter by lazy {
        ValCursAdapter(requireContext(), valuteList, ::onItemValute)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        swipeRefresh()
        setupViews()
        setupViewModel()
    }

    private fun setupToolbar() {
        with(viewBinding) {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit_favorites -> callFavoriteEdit()
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun swipeRefresh() = with(viewBinding) {
        swipe.setColorSchemeResources(
            R.color.black_second
        )
        swipe.setOnRefreshListener {
            viewModel.getRemoteValutes(Utils.getDate(), PATH_EXP)
        }
    }


    private fun callFavoriteEdit() {
        val action = HomeFragmentDirections.actionNavigationHomeToEditFragment(FAVORITE_VALUTE)
        findNavController().navigate(action)
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = valCursAdapter
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getFavoriteLocalValutes().collect {
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

    @SuppressLint("NotifyDataSetChanged")
    private fun getAllValuteSuccess(valutes: List<Valute>) {
        valuteList.clear()
        valuteList.addAll(valutes)
        valCursAdapter.notifyDataSetChanged()
    }

    private fun onItemValute(item: Valute?, position: Int) {
        if (item != null) {
            val intent = Intent(requireContext(), ValuteActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("click", position)
            startActivity(intent)
        }
    }
}