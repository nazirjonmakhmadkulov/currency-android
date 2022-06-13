package com.developer.valyutaapp.ui.chart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.valyutaapp.R
import com.developer.valyutaapp.core.common.Result
import com.developer.valyutaapp.databinding.FragmentChartBinding
import com.developer.valyutaapp.domain.entities.History
import com.developer.valyutaapp.domain.entities.ValCurs
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.utils.ImageResource
import com.developer.valyutaapp.utils.Utils
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList


class ChartFragment : Fragment(R.layout.fragment_chart) {

    private val viewBinding by viewBinding(FragmentChartBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    private val args: ChartFragmentArgs by navArgs()

    private var dateItems: MutableList<String> = mutableListOf()

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

        viewModel.getRemoteValutes.observe(viewLifecycleOwner) {
            subscribeHistoryState(it)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getLocalHistories(args.valId).collect {
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

    private fun subscribeHistoryState(it: Result<ValCurs>) {
        when (it) {
            is Result.Loading -> {}
            is Result.Success -> {}
            is Result.Error -> {
                Log.d("Error ", it.code.toString() + " == " + it.errorMessage)
            }
        }
    }

    private fun getAllValuteSuccess(valutes: List<History>) {
        dateItems.addAll(valutes.map { it.dates })
        showBarChart(valutes)
    }

    private fun showBarChart(valutes: List<History>) {
        val entries = ArrayList<Entry>()
        val title = "color"

        val xAxis: XAxis = viewBinding.chart.xAxis
        xAxis.setDrawGridLines(true)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        xAxis.setDrawLimitLinesBehindData(true)

        if (valutes.size > 2) {
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return Utils.dateFormatChart(valutes[value.toInt()].dates)
                }
            }
        }
        //fit the data into a bar
        for (i in valutes.indices) {
            val barEntry = Entry(i.toFloat(), valutes[i].value.toFloat())
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