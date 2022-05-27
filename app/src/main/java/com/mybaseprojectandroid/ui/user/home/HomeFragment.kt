package com.mybaseprojectandroid.ui.user.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentHomeBinding
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var linelist : ArrayList<Entry>
    lateinit var lineDataSet: LineDataSet
    lateinit var lineData : LineData
    companion object{
        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory(binding.rvItemCard)
    }

    private lateinit var binding : FragmentHomeBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        viewModel.setData()
        setGraphHbA1C()
        setGraphLBS()




    }

    private fun setGraphLBS() {
        var dataSets: ArrayList<ILineDataSet?> = ArrayList()
        val xAxisValues: List<String> = ArrayList(
            Arrays.asList(
                "Jan",
                "Feb",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "Decemeber"
            )
        )
        val incomeEntries = getIncomeEntries()
        dataSets = ArrayList()
        val set1: LineDataSet

        set1 = LineDataSet(incomeEntries, "Income")
        set1.color = resources.getColor(R.color.yellow)
        set1.valueTextColor = Color.rgb(55, 70, 73)
        set1.valueTextSize = 10f
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSets.add(set1)

//customization

//customization
        val mLineGraph: LineChart = binding.lineChartLBS
        mLineGraph.setTouchEnabled(true)
        mLineGraph.isDragEnabled = true
        mLineGraph.setScaleEnabled(false)
        mLineGraph.setPinchZoom(false)
        mLineGraph.setDrawGridBackground(false)
        mLineGraph.extraLeftOffset = 15f
        mLineGraph.extraRightOffset = 15f
//to hide background lines
//to hide background lines
        mLineGraph.xAxis.setDrawGridLines(false)
        mLineGraph.axisLeft.setDrawGridLines(false)
        mLineGraph.axisRight.setDrawGridLines(false)

//to hide right Y and top X border

//to hide right Y and top X border
        val rightYAxis = mLineGraph.axisRight
        rightYAxis.isEnabled = false
        val leftYAxis = mLineGraph.axisLeft
        leftYAxis.isEnabled = false
        val topXAxis = mLineGraph.xAxis
        topXAxis.isEnabled = false


        val xAxis = mLineGraph.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        set1.lineWidth = 2f
        set1.circleRadius = 3f
        set1.setDrawValues(false)
        set1.circleHoleColor = resources.getColor(R.color.yellow)
        set1.setCircleColor(resources.getColor(R.color.yellow))

//String setter in x-Axis

//String setter in x-Axis
        mLineGraph.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val data = LineData(dataSets)
        mLineGraph.data = data
        mLineGraph.animateX(2000)
        mLineGraph.invalidate()
        mLineGraph.legend.isEnabled = false
        mLineGraph.description.isEnabled = false

    }

    private fun setGraphHbA1C() {
        var dataSets: ArrayList<ILineDataSet?> = ArrayList()
        val xAxisValues: List<String> = ArrayList(
            Arrays.asList(
                "Jan",
                "Feb",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "Decemeber"
            )
        )
        val incomeEntries = getIncomeEntries()
        dataSets = ArrayList()
        val set1: LineDataSet

        set1 = LineDataSet(incomeEntries, "Income")
        set1.color = Color.rgb(65, 168, 121)
        set1.valueTextColor = Color.rgb(55, 70, 73)
        set1.valueTextSize = 10f
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSets.add(set1)

//customization

//customization
        val mLineGraph: LineChart = binding.lineChart
        mLineGraph.setTouchEnabled(true)
        mLineGraph.isDragEnabled = true
        mLineGraph.setScaleEnabled(false)
        mLineGraph.setPinchZoom(false)
        mLineGraph.setDrawGridBackground(false)
        mLineGraph.extraLeftOffset = 15f
        mLineGraph.extraRightOffset = 15f
//to hide background lines
//to hide background lines
        mLineGraph.xAxis.setDrawGridLines(false)
        mLineGraph.axisLeft.setDrawGridLines(false)
        mLineGraph.axisRight.setDrawGridLines(false)

//to hide right Y and top X border

//to hide right Y and top X border
        val rightYAxis = mLineGraph.axisRight
        rightYAxis.isEnabled = false
        val leftYAxis = mLineGraph.axisLeft
        leftYAxis.isEnabled = false
        val topXAxis = mLineGraph.xAxis
        topXAxis.isEnabled = false


        val xAxis = mLineGraph.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        set1.lineWidth = 2f
        set1.circleRadius = 3f
        set1.setDrawValues(false)
        set1.circleHoleColor = resources.getColor(R.color.primary_color)
        set1.setCircleColor(resources.getColor(R.color.primary_color))

//String setter in x-Axis

//String setter in x-Axis
        mLineGraph.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val data = LineData(dataSets)
        mLineGraph.data = data
        mLineGraph.animateX(2000)
        mLineGraph.invalidate()
        mLineGraph.legend.isEnabled = false
        mLineGraph.description.isEnabled = false
    }

    private fun getIncomeEntries(): List<Entry>? {
        val incomeEntries: ArrayList<Entry> = ArrayList()
        incomeEntries.add(Entry(1f, 11300f))
        incomeEntries.add(Entry(2f, 1390f))
        incomeEntries.add(Entry(3f, 1190f))
        incomeEntries.add(Entry(4f, 7200f))
        incomeEntries.add(Entry(5f, 4790f))
        incomeEntries.add(Entry(6f, 4500f))
        incomeEntries.add(Entry(7f, 8000f))
        incomeEntries.add(Entry(8f, 7034f))
        incomeEntries.add(Entry(9f, 4307f))
        incomeEntries.add(Entry(10f, 8762f))
        incomeEntries.add(Entry(11f, 4355f))
        incomeEntries.add(Entry(12f, 6000f))
        return incomeEntries.subList(0, 12)
    }




}