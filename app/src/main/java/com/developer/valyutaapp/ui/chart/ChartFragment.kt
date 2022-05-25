package com.developer.valyutaapp.ui.chart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.database.SharedPreference
import com.developer.valyutaapp.databinding.FragmentChartBinding
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.utils.ImageResource
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChartFragment : Fragment(R.layout.fragment_chart) {

    private val viewBinding by viewBinding(FragmentChartBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    private val args: ChartFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewModel()
    }

    private fun setupToolbar() = with(viewBinding) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupViewModel() = with(viewBinding) {
        viewModel.getLocalValuteById(args.valId)

        lifecycleScope.launchWhenCreated {
            viewModel.getLocalHistories().collect {
                getAllValuteSuccess(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.getLocalValuteById.observe(viewLifecycleOwner) {
                val bt = ImageResource.getImageRes(requireContext(), it.charCode)
                iconValute.setImageBitmap(bt)
                name.text = it.charCode
                somon.text = it.value
            }
        }
    }

    private fun getAllValuteSuccess(valutes: List<History>) {
        showBarChart(valutes)
    }

    private fun showBarChart(valutes: List<History>) {
        val entries = ArrayList<Entry>()
        val title = "color"
        //fit the data into a bar
        for (i in valutes.indices) {
            val barEntry = Entry(valutes[i].nominal.toFloat(), valutes[i].value.toFloat())
            entries.add(barEntry)
        }
        val barDataSet = LineDataSet(entries, title)
        val data = LineData(barDataSet)
        barDataSet.valueTextSize = 12f
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toMutableList()
        viewBinding.chart.data = data
        viewBinding.chart.invalidate()
    }
}