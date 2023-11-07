package com.developer.currency.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.developer.currency.R
import com.developer.currency.core.common.PATH_EXP
import com.developer.currency.databinding.FragmentChartBinding
import com.developer.currency.domain.entities.History
import com.developer.currency.domain.entities.ValHistory
import com.developer.currency.utils.ImageResource
import com.developer.currency.utils.Utils
import com.developer.currency.utils.Utils.getDate
import com.developer.currency.utils.Utils.getYearAge
import com.developer.currency.utils.launchAndCollectIn
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ChartFragment : Fragment(R.layout.fragment_chart) {
    private val viewBinding by viewBinding(FragmentChartBinding::bind)
    private val viewModel by viewModel<ChartViewModel>()
    private val args: ChartFragmentArgs by navArgs()
    private var dateItems: MutableList<String> = mutableListOf()
    private var limit = 7

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewModel()
        viewBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (view.findViewById(checkedId) as? RadioButton) {
                viewBinding.week -> getLocalHistories(7)
                viewBinding.month -> getLocalHistories(30)
                viewBinding.year -> getLocalHistories(365)
            }
        }
    }

    private fun setupToolbar() = with(viewBinding) {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    private fun setupViewModel() = with(viewBinding) {
        getRemoteHistories(getYearAge())
        viewModel.getLocalValuteById(args.valId)
        viewModel.getRemoteHistories.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            subscribeHistoryState(it)
        }

        viewModel.getLocalValuteById.launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) { valute ->
            valute?.let {
                val bt = ImageResource.getImageRes(requireContext(), it.charCode)
                iconValute.setImageDrawable(bt)
                name.text = it.charCode
                somon.text = it.value
            }
        }
    }

    private fun getRemoteHistories(dates: String) {
        viewBinding.loading.visibility = View.VISIBLE
        viewModel.getRemoteHistories(
            dates,
            getDate(),
            args.valId,
            args.charCode,
            PATH_EXP
        )
    }

    private fun getLocalHistories(limit: Int) {
        viewModel.getLocalHistories(args.valId, limit).launchAndCollectIn(viewLifecycleOwner, Lifecycle.State.STARTED) {
            getAllValuteSuccess(it)
        }
    }

    private fun subscribeHistoryState(result: ValHistory) {
        getLocalHistories(limit)
        viewBinding.loading.visibility = View.GONE
    }

    private fun getAllValuteSuccess(valutes: List<History>) {
        showBarChart(emptyList())
        dateItems.clear()
        dateItems.addAll(valutes.map { it.dates })
        showBarChart(valutes.asReversed())
    }

    private fun showBarChart(valutes: List<History>) = with(viewBinding) {
        val entries = ArrayList<Entry>()
        val title = "color"
        chart.setBackgroundColor(Color.WHITE)
        // no description text
        chart.description.isEnabled = false
        // enable touch gestures
        chart.setTouchEnabled(true)
        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)
        chart.maxHighlightDistance = 300F

        val y = chart.axisLeft
        y.setLabelCount(6, false)
        y.textColor = Color.BLACK
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        y.setDrawGridLines(false)
        y.axisLineColor = Color.WHITE
        chart.axisRight.isEnabled = false
        chart.legend.isEnabled = true

        val xAxis: XAxis = viewBinding.chart.xAxis

        if (valutes.size > 2) {
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return Utils.dateFormatChart(valutes[value.toInt()].dates)
                }
            }
        }
        // fit the data into a bar
        for (i in valutes.indices) {
            val barEntry = Entry(i.toFloat(), valutes[i].value.toFloat())
            entries.add(barEntry)
        }
        val barDataSet = LineDataSet(entries, title)
        val data = LineData(barDataSet)
        barDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        barDataSet.cubicIntensity = 0.2f
        barDataSet.setDrawFilled(true)
        barDataSet.setDrawCircles(false)
        barDataSet.lineWidth = 1.8f
        barDataSet.circleRadius = 4f
        barDataSet.setCircleColor(Color.rgb(104, 248, 175))
        barDataSet.highLightColor = Color.rgb(104, 248, 175)
        barDataSet.color = Color.rgb(104, 248, 175)
        barDataSet.fillColor = Color.rgb(104, 248, 175)
        barDataSet.fillAlpha = 100
        barDataSet.setDrawHorizontalHighlightIndicator(false)
        barDataSet.fillFormatter = IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }

        barDataSet.valueTextSize = 12f
        data.setDrawValues(false)
        viewBinding.chart.data = data
        viewBinding.chart.invalidate()
    }
}