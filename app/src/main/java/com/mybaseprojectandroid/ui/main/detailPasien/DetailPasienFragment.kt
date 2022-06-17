package com.mybaseprojectandroid.ui.main.detailPasien

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.mybaseprojectandroid.databinding.FragmentDetailPasienBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.model.Pemeriksaan
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.getColor
import com.mybaseprojectandroid.utils.system.popNavigation
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.vo.DateData


class DetailPasienFragment : Fragment(R.layout.fragment_detail_pasien) {

    private lateinit var binding: FragmentDetailPasienBinding

    private val viewModel: DetailPasienViewModel by viewModels {
        FactoryViewModel(DetailPasienViewModel(FirebaseDatabase()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailPasienBinding.bind(view)

        val namaLengkap = arguments?.getString(Constant.KEY_NAMA_LENGKAP)
        val idUser = arguments?.getString(Constant.KEY_ID_USER)

        viewModel.setAktivitas(idUser!!)
        viewModel.setGrafikHbA1C(idUser)
        viewModel.setGrafikLBS(idUser)

        binding.nama.text = namaLengkap

        getAktivitas()

        getHbA1C()

        getDataLBS()

        binding.back.setOnClickListener {
            popNavigation(requireView())
        }

    }

    private fun getAktivitas() {
        viewModel.responseAktivitas.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot

                    val data = querySnapshot.toObjects<Aktivitas>()

                    data.forEach { aktivitas ->
                        var day = aktivitas.startDate?.day
                        val month = aktivitas.startDate?.month
                        val year = aktivitas.startDate?.year

                        val sumWeekBring = aktivitas.sumWeekBring

                        showLogAssert("sumWeekBring", "$sumWeekBring")

                        val colorRed = getColor(requireContext(), R.color.red)
                        val colorGreen = getColor(requireContext(), R.color.primary_color)

                        if (sumWeekBring!! < 7) {
                            setDate(day!!, month!!, year!!, colorRed)
                        } else {
                            setDate(day!!, month!!, year!!, colorGreen)
                        }
                    }

                }
                is Response.Error -> TODO()
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun setDate(day: Int, month: Int, year: Int, color: Int) {
        var day1 = day
        for (i in 1..7) {
            binding.calendar.markDate(
                DateData(year, month, day1).setMarkStyle(
                    MarkStyle(MarkStyle.BACKGROUND, color)
                )
            )
            day1 += 1
        }
    }

    private fun getHbA1C() {
        viewModel.responseHbA1C.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot

                    val data = querySnapshot.toObjects<Aktivitas>()

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
                is Response.Error -> TODO()
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun getDataLBS() {
        viewModel.responseLBS.observe(viewLifecycleOwner) { it ->
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

    private fun setGraphHbA1C(xAxisValues: ArrayList<String>, yAxisValues: ArrayList<Float>) {

        val entryList = getEntryList(yAxisValues)

        val dataSets: ArrayList<ILineDataSet?> = ArrayList()

        val set1 = LineDataSet(entryList, "Income")
        set1.color = Color.rgb(65, 168, 121)
        set1.valueTextColor = Color.rgb(55, 70, 73)
        set1.valueTextSize = 10f
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSets.add(set1)

//customization

//customization
        val mLineGraph: LineChart = binding.lineChartHB1AC
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
        val mLineGraph: LineChart = binding.lineCHartLBS
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