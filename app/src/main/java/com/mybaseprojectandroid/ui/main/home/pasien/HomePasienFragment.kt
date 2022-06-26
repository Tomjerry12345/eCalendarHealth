package com.mybaseprojectandroid.ui.main.home.pasien

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentHomePasienBinding
import com.mybaseprojectandroid.model.Pemeriksaan
import com.mybaseprojectandroid.ui.main.home.adapter.CardAdapter
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.system.PdfUtils
import com.mybaseprojectandroid.utils.system.getColor
import com.mybaseprojectandroid.utils.system.moveNavigationTo
import com.mybaseprojectandroid.utils.widget.DialogProgress
import com.mybaseprojectandroid.utils.widget.RecyclerViewUtils

class HomePasienFragment : Fragment(R.layout.fragment_home_pasien) {

    companion object {
        fun newInstance(): HomePasienFragment {
            return HomePasienFragment()
        }
    }

    private lateinit var binding: FragmentHomePasienBinding

    private val pasienViewModel: HomePasienViewModel by viewModels {
        FactoryViewModel(HomePasienViewModel(FirebaseDatabase()))
    }

//    private val pasien = getSavedPasien()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomePasienBinding.bind(view)
//        binding.lifecycleOwner = this
        binding.viewModel = pasienViewModel

//        setRecyclerView()
//
//        getDataHbA1C()
//        getDataLBS()
//
//        binding.tvTitle.text = "Hi, ${pasien?.namaLengkap}"
//        binding.parentTestimoni.setOnClickListener {
//            moveNavigationTo(binding.view, R.id.testimoniFragment)
//        }

    }

    private fun setRecyclerView() {
        val dialog = DialogProgress.initDialog(requireContext())
        val adapterr = CardAdapter(Constant.listCardItem, object : RecyclerViewUtils {
            override fun clicked() {
                pasienViewModel.isReadingDocument().observe(viewLifecycleOwner) {
                    when (it) {
                        is Response.Changed -> TODO()
                        is Response.Error -> {
                            dialog.dismiss()
                            showToast(requireContext(), it.error)
                        }
                        is Response.Progress -> {
                            dialog.show()
                        }
                        is Response.Success -> {
                            dialog.dismiss()
                            val pdfUtils = PdfUtils(requireActivity())
//                            pdfUtils.openPdf(pdfUtils.PATH_DOCUMENT, "edukasi.pdf")
                            pdfUtils.openPdfInRaw(
                                pdfUtils.PATH_DOCUMENT,
                                "edukasi.pdf",
                                R.raw.edukasi
                            )
                        }
                    }
                }

            }
        })
        binding.rvItemCard.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterr
        }
    }

    private fun getDataHbA1C() {
        pasienViewModel.dataHbA1C.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot

                    val dataHbA1C = querySnapshot.toObjects<Pemeriksaan>()

                    val xAxis = ArrayList<String>()
                    val yAxis = ArrayList<Float>()

                    val sorting = dataHbA1C.sortedBy { pemeriksaan ->
                        pemeriksaan.timeStamp
                    }

                    sorting.forEach { pemeriksaan ->
                        pemeriksaan.tanggal?.let { it1 -> xAxis.add(it1) }
                        pemeriksaan.nilai?.let { it1 -> yAxis.add(it1) }
                    }

                    setGraphHbA1C(xAxis, yAxis)
                }
                is Response.Error -> {
                    showLogAssert("error", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun getDataLBS() {
        pasienViewModel.dataLBS.observe(viewLifecycleOwner) { it ->
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot

                    val dataLBS = querySnapshot.toObjects<Pemeriksaan>()

                    val xAxis = ArrayList<String>()
                    val yAxis = ArrayList<Float>()

                    val sorting = dataLBS.sortedBy { pemeriksaan ->
                        pemeriksaan.timeStamp
                    }

                    sorting.forEach { pemeriksaan ->
                        pemeriksaan.tanggal?.let { it1 -> xAxis.add(it1) }
                        pemeriksaan.nilai?.let { it1 -> yAxis.add(it1) }
                    }

                    setGraphLBS(xAxis, yAxis)
                }
                is Response.Error -> {
                    showLogAssert("error", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun setGraphLBS(xAxisValues: ArrayList<String>, yAxisValues: ArrayList<Float>) {

        val entryList = getEntryList(yAxisValues)

        val dataSets: ArrayList<ILineDataSet?> = ArrayList()

        val set1 = LineDataSet(entryList, "LBS")
        set1.color = ContextCompat.getColor(requireContext(), R.color.yellow)
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
        leftYAxis.isEnabled = true
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
        set1.circleHoleColor = getColor(requireContext(), R.color.yellow)
        set1.setCircleColor(getColor(requireContext(), R.color.yellow))

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

    private fun setGraphHbA1C(xAxisValues: ArrayList<String>, yAxisValues: ArrayList<Float>) {

        val entryList = getEntryList(yAxisValues)

        val dataSets: ArrayList<ILineDataSet?> = ArrayList()

        val set1 = LineDataSet(entryList, "hHbA1C")
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
        leftYAxis.isEnabled = true
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
        set1.circleHoleColor = ContextCompat.getColor(requireContext(), R.color.primary_color)
        set1.setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary_color))

//String setter in x-Axis

//String setter in x-Axis
        mLineGraph.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val data = LineData(dataSets)
        mLineGraph.data = data
        mLineGraph.animateX(2000)
        mLineGraph.invalidate()
        mLineGraph.legend.isEnabled = false
        mLineGraph.description.isEnabled = false

//        mLineGraph.axisLeft.addLimitLine(getLimitByType())
    }

    private fun getEntryList(yAxisValues: ArrayList<Float>): List<Entry> {
        val entries: ArrayList<Entry> = ArrayList()

        var x = 0f

        for (y in yAxisValues) {
            entries.add(Entry(x, y))
            x += 1
        }

        return entries
    }

}