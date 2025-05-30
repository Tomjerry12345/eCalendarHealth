package com.mybaseprojectandroid.ui.main.home.pasien

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
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
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentHomePasienBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.model.DateModel
import com.mybaseprojectandroid.model.Pemeriksaan
import com.mybaseprojectandroid.service.NotifReceiver
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.ui.main.home.adapter.CardAdapter
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.local.setSavedContentMessageNotif
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.system.*
import com.mybaseprojectandroid.utils.widget.DialogProgress
import com.mybaseprojectandroid.utils.widget.RecyclerViewUtils

@RequiresApi(Build.VERSION_CODES.O)
class HomePasienFragment : Fragment(R.layout.fragment_home_pasien) {

    companion object {
        fun newInstance(): HomePasienFragment {
            return HomePasienFragment()
        }
    }

    private lateinit var binding: FragmentHomePasienBinding

    private val viewModel: HomePasienViewModel by viewModels {
        FactoryViewModel(HomePasienViewModel(FirebaseDatabase()))
    }

    private val pasien = getSavedPasien()


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomePasienBinding.bind(view)
        binding.viewModel = viewModel

        setSavedContentMessageNotif("")

        getData()
        getDataHbA1C()
        getDataLBS()
        binding.tvTitle.text = "Hi, ${pasien?.namaLengkap}"
        binding.parentTestimoni.setOnClickListener {
            moveNavigationTo(binding.view, R.id.testimoniFragment)
        }


    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val data = it.data as QuerySnapshot
                    val dataExtract = data.toObjects<Aktivitas>()

                    val dayNow = DateCustom.getDayNow()
                    val monthNow = DateCustom.getMonthNow()
                    val yearNow = DateCustom.getYearNow()
                    val hoursNow = DateCustom.getHoursNow()
                    var weekOfMonth = DateCustom.getWeeksMonth()

                    var isUpdateAktivitas = false
                    var callGetData = false

                    var message = ""

                    if (dataExtract.isNotEmpty()) {
                        val dataAktivitas = dataExtract[0]

                        val sumWeek = dataAktivitas.sumWeekBring
                        val dateUpdateDay = dataAktivitas.dateUpdate?.day!!
                        val endDateDay = dataAktivitas.endDate?.day!!
                        val startDateDay = dataAktivitas.startDate?.day!!

                        when (sumWeek) {
                            Constant.START -> {
                                message =
                                    "Kamu belum beraktifitas di minggu ini, yuk mulai sekarang! "
                                NotifReceiver().setReminder(requireContext(), message)
                            }

                            Constant.END -> {
                                message =
                                    "Selamat, target aktifitas minggu ini sudah terpenuhi, tetap konsisten ya!"
                                NotifReceiver().stopReminder(requireContext())
                            }
                            else -> {
                                message =
                                    "Minggu ini kamu masih ada ${Constant.END - sumWeek!!} aktifitas lagi nih, semangat! "
                                NotifReceiver().setReminder(requireContext(), message)
                            }
                        }

                        binding.txtPeringatan.text = message

//                        dayNow = 10
//                        weekOfMonth = 1

                        var setDay = dayNow + 1

                        if (setDay > DateCustom.getLastInMonth()) {
                            setDay = 1
                        }

                        if (dayNow == dateUpdateDay) {
                            showLogAssert("dayNow == dateUpdateDay", "$dayNow => ${dateUpdateDay}")
                            showLogAssert(
                                "dataAktivitas.dateUpdate?.hours!! >= hoursNow",
                                "${dataAktivitas.dateUpdate?.hours!!} => ${hoursNow}"
                            )
                            if (hoursNow >= dataAktivitas.dateUpdate?.hours!!) {
                                dataAktivitas.sumDayBring = 0
                                onDateUpdate(dataAktivitas, setDay, monthNow, yearNow, hoursNow)
                                isUpdateAktivitas = true
                            }
                        }

                        else if (dayNow > dateUpdateDay) {
                            showLogAssert("test", "$dayNow => ${dateUpdateDay}")

                            if (dataAktivitas.week!! >= 5) {
                                if (dayNow >= startDateDay && dayNow <= DateCustom.getLastInMonth()) {
                                    showLogAssert("dayNow > dateUpdateDay", "rentang akhir bulan")
                                } else {
                                    onDateUpdate(dataAktivitas, setDay, monthNow, yearNow, hoursNow)
                                    isUpdateAktivitas = true
                                }
                            } else {
                                dataAktivitas.sumDayBring = 0
                                onDateUpdate(dataAktivitas, setDay, monthNow, yearNow, hoursNow)
                                isUpdateAktivitas = true
                            }

                        }

                        if (dayNow > endDateDay) {
                            showLogAssert("test", "dayNow > endDateDay == $dayNow > $endDateDay")

                            if (dataAktivitas.week!! >= 5) {
                                if (dayNow >= startDateDay && dayNow <= DateCustom.getLastInMonth()) {
                                    showLogAssert("test", "rentang akhir bulan")
                                } else {
                                    setDay = endDateDay
                                    onDateUpdate(dataAktivitas, setDay, monthNow, yearNow, hoursNow)
                                    dataAktivitas.isUpdate = false
                                    callGetData = true
                                }
                            } else {
                                setDay = endDateDay
                                onDateUpdate(dataAktivitas, setDay, monthNow, yearNow, hoursNow)
                                dataAktivitas.isUpdate = false
                                callGetData = true
                            }

                            isUpdateAktivitas = true

                        }
                        else if (weekOfMonth > dataAktivitas.week!!) {
                            showLogAssert("test", "weekOfMonth > dataAktivitas.week!!")
                            dataAktivitas.isUpdate = false
                            isUpdateAktivitas = true
                        }

                        if (isUpdateAktivitas) {
                            viewModel.updateAktivitas(dataAktivitas)
                                .observe(viewLifecycleOwner) { response ->
                                    when (response) {
                                        is Response.Changed -> TODO()
                                        is Response.Error -> showLogAssert(
                                            "error isUpdateAktivitas",
                                            response.error
                                        )
                                        is Response.Progress -> TODO()
                                        is Response.Success -> {
                                            if (callGetData) {
                                                moveIntentTo(
                                                    requireActivity(),
                                                    BaseActivity(),
                                                    true
                                                )
                                            }
                                            setRecyclerView(dataAktivitas)
                                        }
                                    }
                                }
                        }
                        else {
                            setRecyclerView(dataAktivitas)
                        }

                    } else {
                        message = "Kamu belum beraktifitas di minggu ini, yuk mulai sekarang! "
                        binding.txtPeringatan.text = message
                        NotifReceiver().setReminder(requireContext(), message)
                        setRecyclerView(null)
                    }

                    setSavedContentMessageNotif(message)

                }

                is Response.Error -> {
                    showLogAssert("error", it.error)
                }

                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun onDateUpdate(
        dataAktivitas: Aktivitas,
        setDay: Int,
        monthNow: Int,
        yearNow: Int,
        hoursNow: Int
    ) {
        dataAktivitas.dateUpdate = DateModel(
            day = setDay,
            month = monthNow,
            year = yearNow,
            hours = hoursNow
        )
    }

    private fun setRecyclerView(aktivitas: Aktivitas?) {
        val dialog = DialogProgress.initDialog(requireContext())
        val adapterr = context?.let {
            CardAdapter(it, Constant.listCardItem, object : RecyclerViewUtils {
                override fun clicked() {
                    viewModel.isReadingDocument().observe(viewLifecycleOwner) {
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
                                    pdfUtils.pathDocument,
                                    "edukasi.pdf",
                                    R.raw.edukasi
                                )
                            }
                        }
                    }

                }
            }, aktivitas?.sumDayBring, aktivitas?.sumWeekBring)
        }
        binding.rvItemCard.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterr
        }
    }

    private fun getDataHbA1C() {
        viewModel.dataHbA1C.observe(viewLifecycleOwner) {
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
        viewModel.dataLBS.observe(viewLifecycleOwner) { it ->
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
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSets.add(set1)

        val mLineGraph: LineChart = binding.lineChartLBS
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

        val mLineGraph: LineChart = binding.lineChart
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