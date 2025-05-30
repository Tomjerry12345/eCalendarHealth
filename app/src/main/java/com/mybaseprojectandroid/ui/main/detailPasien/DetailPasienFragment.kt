package com.mybaseprojectandroid.ui.main.detailPasien

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentDetailPasienBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.model.Pemeriksaan
import com.mybaseprojectandroid.ui.main.calendar.adapter.DayViewContainer
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.getColor
import com.mybaseprojectandroid.utils.system.moveNavigationTo
import com.mybaseprojectandroid.utils.system.popNavigation
import java.time.LocalDate
import java.time.YearMonth


class DetailPasienFragment : Fragment(R.layout.fragment_detail_pasien) {

    private lateinit var binding: FragmentDetailPasienBinding

    private val viewModel: DetailPasienViewModel by viewModels {
        FactoryViewModel(DetailPasienViewModel(FirebaseDatabase()))
    }

    private val markedDates: MutableMap<LocalDate, Int> = mutableMapOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailPasienBinding.bind(view)

        val namaLengkap = arguments?.getString(Constant.KEY_NAMA_LENGKAP)
        val idUser = arguments?.getString(Constant.KEY_ID_USER)
        val persen = arguments?.getInt(Constant.KEY_PERSEN, 0)
        val isReadingDocument = arguments?.getInt(Constant.KEY_IS_READING_DOCUMENT, 0)

        viewModel.setAktivitas(idUser!!)
        viewModel.setGrafikHbA1C(idUser)
        viewModel.setGrafikLBS(idUser)

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
            // Handle the back button event
            back()
        }

        binding.nama.text = namaLengkap
        binding.txtIsReading.text = "$isReadingDocument %"
        binding.txtPersen.text = "$persen %"
        binding.progresss.progress = persen!!
        binding.progresssBuku.progress = isReadingDocument!!

        setupCalendar()

        getAktivitas()
        getHbA1C()
        getDataLBS()

        binding.back.setOnClickListener {
            back()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupCalendar() {
        binding.calendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer = DayViewContainer(view)

            @SuppressLint("ResourceType")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                val textView = container.textView
                textView.text = data.date.dayOfMonth.toString()
                if (data.position == DayPosition.MonthDate) {
                    textView.visibility = View.VISIBLE
                    if (markedDates.containsKey(data.date)) {
                        textView.setTextColor(Color.WHITE)
                        textView.setBackgroundColor(markedDates[data.date] ?: Color.GREEN)
                    } else {
                        textView.setTextColor(Color.BLACK)
                        textView.background = null
                    }
                } else {
                    textView.visibility = View.INVISIBLE
                }
            }
        }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val firstDayOfWeek = firstDayOfWeekFromLocale()

        binding.calendar.setup(startMonth, endMonth, firstDayOfWeek)
        binding.calendar.scrollToMonth(currentMonth)
    }

    fun back() {
//        moveNavigationTo(requireView(), R.id.action_detailPasienFragment_to_listPasienFragment)
        popNavigation(requireView())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAktivitas() {
        viewModel.responseAktivitas.observe(viewLifecycleOwner) {
////            showLogAssert("getAktivitas", "$it")
            binding.calendar.visibility = View.GONE
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot

                    val data = querySnapshot.toObjects<Aktivitas>()

                    data.forEach { aktivitas ->
                        val day = aktivitas.startDate?.day
                        val month = aktivitas.startDate?.month
                        val year = aktivitas.startDate?.year

                        val sumWeekBring = aktivitas.sumWeekBring

                        showLogAssert("sumWeekBring", "$sumWeekBring")

//                        val colorRed = getColor(requireContext(), R.color.red)
//                        val colorGreen = getColor(requireContext(), R.color.primary_color)
//
//                        if (sumWeekBring!! < 7) {
//                            setDate(day!!, month!!, year!!, colorRed)
//                        } else {
//                            setDate(day!!, month!!, year!!, colorGreen)
//                        }
                        val color1 = getColor(requireContext(), R.color.color1)
                        val color2 = getColor(requireContext(), R.color.color2)
                        val color3 = getColor(requireContext(), R.color.color3)
                        val color4 = getColor(requireContext(), R.color.color4)
                        val color5 = getColor(requireContext(), R.color.color5)

                        when(sumWeekBring) {
                            1 -> setDate(day!!, month!!, year!!, color1)
                            2 -> setDate(day!!, month!!, year!!, color2)
                            3 -> setDate(day!!, month!!, year!!, color3)
                            4 -> setDate(day!!, month!!, year!!, color4)
                            5 -> setDate(day!!, month!!, year!!, color5)
                        }
                    }

                }
                is Response.Error -> TODO()
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

//    private fun setDate(day: Int, month: Int, year: Int, color: Int) {
////        binding.calendar.visibility = View.VISIBLE
////        var day1 = day
////        for (i in 1..7) {
////            binding.calendar.markDate(
////                DateData(year, month, day1).setMarkStyle(
////                    MarkStyle(MarkStyle.BACKGROUND, color)
////                )
////            )
////            day1 += 1
////        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDate(day: Int, month: Int, year: Int, color: Int) {
        binding.calendar.visibility = View.VISIBLE
        var day1 = day
        for (i in 1..7) {
            val date = LocalDate.of(year, month, day1)
            markedDates[date] = color
            day1 += 1
        }

        showLogAssert("markedDates", markedDates)

        binding.calendar.notifyCalendarChanged()
    }



    override fun onDestroy() {
        super.onDestroy()
//        binding.calendar.markedDates.all.clear()
    }

    private fun getHbA1C() {
        binding.lineChartHB1AC.visibility = View.GONE
        viewModel.responseHbA1C.observe(viewLifecycleOwner) {
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

                    binding.lineChartHB1AC.visibility = View.VISIBLE

                }
                is Response.Error -> TODO()
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun getDataLBS() {
        binding.lineCHartLBS.visibility = View.GONE
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

                    binding.lineCHartLBS.visibility = View.VISIBLE
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
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSets.add(set1)

        val mLineGraph: LineChart = binding.lineCHartLBS
        mLineGraph.setTouchEnabled(true)
        mLineGraph.isDragEnabled = true
        mLineGraph.setScaleEnabled(false)
        mLineGraph.setPinchZoom(false)
        mLineGraph.setDrawGridBackground(false)

        mLineGraph.xAxis.setDrawGridLines(false)
        mLineGraph.axisLeft.setDrawGridLines(false)
        mLineGraph.axisRight.setDrawGridLines(false)

        val rightYAxis = mLineGraph.axisRight
        rightYAxis.isEnabled = false
        val leftYAxis = mLineGraph.axisLeft
        leftYAxis.isEnabled = true
        val topXAxis = mLineGraph.xAxis
        topXAxis.isEnabled = false

        val xAxis = mLineGraph.xAxis
        xAxis.granularity = 1f
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        set1.circleHoleColor = getColor(requireContext(), R.color.yellow)
        set1.setCircleColor(getColor(requireContext(), R.color.yellow))

        mLineGraph.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val data = LineData(dataSets)
        mLineGraph.data = data
        mLineGraph.animateX(2000, Easing.EaseInSine)
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
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSets.add(set1)

        val mLineGraph: LineChart = binding.lineChartHB1AC
        mLineGraph.setTouchEnabled(true)
        mLineGraph.isDragEnabled = true
        mLineGraph.setScaleEnabled(true)
        mLineGraph.setPinchZoom(false)
        mLineGraph.setDrawGridBackground(false)

        mLineGraph.xAxis.setDrawGridLines(false)
        mLineGraph.axisLeft.setDrawGridLines(false)
        mLineGraph.axisRight.setDrawGridLines(false)

        val rightYAxis = mLineGraph.axisRight
        rightYAxis.isEnabled = false
        val leftYAxis = mLineGraph.axisLeft
        leftYAxis.isEnabled = true
        val topXAxis = mLineGraph.xAxis
        topXAxis.isEnabled = false

        val xAxis = mLineGraph.xAxis
        xAxis.isEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f

        set1.circleHoleColor = ContextCompat.getColor(requireContext(), R.color.primary_color)
        set1.setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary_color))

        mLineGraph.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val data = LineData(dataSets)
        mLineGraph.data = data
        mLineGraph.animateX(2000, Easing.EaseInSine)
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