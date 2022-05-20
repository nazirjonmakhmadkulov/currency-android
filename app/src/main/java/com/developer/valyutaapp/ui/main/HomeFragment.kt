package com.developer.valyutaapp.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentHomeBinding
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.domain.entities.Valute
import com.developer.valyutaapp.ui.ValuteViewModel
import com.developer.valyutaapp.ui.adapter.ValCursAdapter
import com.developer.valyutaapp.ui.valute.ValuteActivity
import com.developer.valyutaapp.utils.Utils
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModel<ValuteViewModel>()
    private val prefs: SharedPreference by inject()

    var valuteList: MutableList<Valute> = ArrayList()

    private val valCursAdapter by lazy {
        ValCursAdapter(requireContext(), valuteList, ::onItemValute)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        viewBinding.recyclerValCurs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = valCursAdapter
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launchWhenCreated {
            viewModel.getLocalValutes().collect {
                getAllValuteSuccess(it)
            }
        }
        viewModel.getRemoteValutes(Utils.getDate(), PATH_EXP)
        viewModel.getRemoteValutes.observe(viewLifecycleOwner) {
            subscribeValuteState(it)
        }
    }

    private fun subscribeValuteState(it: Result<ValCurs>) {
        when (it) {
            is Result.Loading -> {}
            is Result.Success -> {
                Log.d("Success ", it.data.valute.toString())
            }
            is Result.Error -> {
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

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == R.id.setting) {
//            val intent = Intent(this, SettingActivity::class.java)
//            startActivity(intent)
//        } else if (id == R.id.update) {
//            setupViews()
//            viewModel.getRemoteValutes()
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun onItemValute(item: Valute?, position: Int) {
        if (item != null) {
            val intent = Intent(requireContext(), ValuteActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("click", position)
            startActivity(intent)
        }
    }
}