package com.mybaseprojectandroid.utils.system

import android.os.Build
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.mybaseprojectandroid.model.DateBringWalking
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.IndexedColorMap
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ExcellUtils(
    private val activity: ComponentActivity,
    private val dataPasienModel: List<PasienModel>,
    val dateBringWalking: ArrayList<List<DateBringWalking>>
) {

    val HEADER_DATA_PASIEN = listOf(
        "username",
        "alamat",
        "umur",
        "tanggal_lahir",
        "lama_terdiagnosa",
        "pengobatan",
        "pendamping",
    )

    val HEADER_AKTIVITAS = listOf(
        "nama",
        "tanggal bring walking 1",
        "jam bring walking 1",
        "status",
        "tanggal bring walking 2",
        "jam bring walking 2",
        "status",
        "tanggal bring walking 3",
        "jam bring walking 3",
        "status",
        "tanggal bring walking 4",
        "jam bring walking 4",
        "status",
    )

    val path =
        Environment.getExternalStorageDirectory().path + "/" + Environment.DIRECTORY_DOCUMENTS

    val listNama = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun createWorkbook(): Workbook {
        // Creating excel workbook
        val workbook = XSSFWorkbook()

        //Create Header Cell Style
        val cellStyle = getHeaderStyle(workbook)

        if (dataPasienModel.isNotEmpty()) {
            val sheet: Sheet = workbook.createSheet("identitas")
            createSheetHeader(cellStyle, sheet, HEADER_DATA_PASIEN)
            //Adding data to the sheet
            var i = 1
            dataPasienModel.forEach {
                it.namaLengkap?.let { it1 -> listNama.add(it1) }
                addDataIdentitas(i, sheet, it)
                i += 1
            }
        }

        if (dateBringWalking.isNotEmpty()) {
            val sheetAktivitas1: Sheet = workbook.createSheet("aktivitas Minggu 1")
            val sheetAktivitas2: Sheet = workbook.createSheet("aktivitas Minggu 2")
            val sheetAktivitas3: Sheet = workbook.createSheet("aktivitas Minggu 3")
            val sheetAktivitas4: Sheet = workbook.createSheet("aktivitas Minggu 4")

            createSheetHeader(cellStyle, sheetAktivitas1, HEADER_AKTIVITAS)
            createSheetHeader(cellStyle, sheetAktivitas2, HEADER_AKTIVITAS)
            createSheetHeader(cellStyle, sheetAktivitas3, HEADER_AKTIVITAS)
            createSheetHeader(cellStyle, sheetAktivitas4, HEADER_AKTIVITAS)
            //Adding data to the sheet

            var isUpdate1 = true
            var isUpdate2 = true
            var isUpdate3 = true
            var isUpdate4 = true

            var row1 = 1
            var row2 = 1
            var row3 = 1
            var row4 = 1

            var sumBring = 0

            var i = 0
            var j = 0
            var k = 0
            var l = 0

            var y = 0


            dateBringWalking.forEach {
                val sheet1 = sheetAktivitas1.createRow(row1)
                val sheet2 = sheetAktivitas2.createRow(row2)
                val sheet3 = sheetAktivitas3.createRow(row3)
                val sheet4 = sheetAktivitas4.createRow(row4)

                val namaLengkap = listNama[y]

                it.forEach { dateBringWalking ->
                    val day = dateBringWalking.date?.day
                    val month =
                        dateBringWalking.date?.month?.let { it1 -> DateCustom.getNameMonth(it1) }
                    val year = dateBringWalking.date?.year
                    val hours = dateBringWalking.date?.hours
                    val week = dateBringWalking.week

                    val tanggal = "$day $month $year"

                    if (week == 1) {
                        showLogAssert("week 1", "true")
                        isUpdate1 = setNamaLengkap(isUpdate1, sheet1, namaLengkap)
                        cellAktivitas(sheet1, tanggal, hours!!, i)
                        if (sumBring == it.size - 2) {
                            sumBring = 0
                            i = 0
                            isUpdate1 = true
                            row1++
                        } else {
                            i += 3
                            sumBring++
                        }
                    } else if (week == 2) {
                        isUpdate2 = setNamaLengkap(isUpdate2, sheet2, namaLengkap)
                        cellAktivitas(sheet2, tanggal, hours!!, j)
                        if (sumBring == it.size - 2) {
                            sumBring = 0
                            j = 0
                            isUpdate2 = true
                            row2++
                        } else {
                            j += 3
                            sumBring++
                        }
                        showLogAssert("week 2", "true")
                    }
                    else if (week == 3) {
                        isUpdate3 = setNamaLengkap(isUpdate3, sheet3, namaLengkap)
                        cellAktivitas(sheet3, tanggal, hours!!, k)
                        if (sumBring == it.size - 2) {
                            sumBring = 0
                            k = 0
                            isUpdate3 = true
                            row3++
                        } else {
                            k += 3
                            sumBring++
                        }

                    }
                    else if (week == 4){
                        isUpdate4 = setNamaLengkap(isUpdate4, sheet4, namaLengkap)
                        cellAktivitas(sheet4, tanggal, hours!!, l)
                        if (sumBring == it.size - 2) {
                            sumBring = 0
                            l = 0
                            isUpdate4 = true
                            row4++
                        } else {
                            l += 3
                            sumBring++
                        }
                    }

                }
                y++
            }

        }

        return workbook
    }

    private fun setNamaLengkap(isUpdate: Boolean, row: Row, namaLengkap: String): Boolean {
        if (isUpdate) {
            createCell(row, 0, namaLengkap)
            return false
        }

        return true
    }

    private fun cellAktivitas(row: Row, tanggal: String, hours: Int, i: Int) {
        createCell(row, i + 1, tanggal)
        createCell(row, i + 2, "$hours")
        createCell(row, i + 3, "TRUE")
    }

    private fun createSheetHeader(
        cellStyle: CellStyle,
        sheet: Sheet,
        header: List<String>
    ) {
        //setHeaderStyle is a custom function written below to add header style

        //Create sheet first row
        val row = sheet.createRow(0)

        //Header list


        //Loop to populate each column of header row
        for ((index, value) in header.withIndex()) {

            val columnWidth = (15 * 500)

            //index represents the column number
            sheet.setColumnWidth(index, columnWidth)

            //Create cell
            val cell = row.createCell(index)

            //value represents the header value from HEADER_LIST
            cell?.setCellValue(value)

            //Apply style to cell
            cell.cellStyle = cellStyle
        }
    }

    private fun getHeaderStyle(workbook: Workbook): CellStyle {

        //Cell style for header row
        val cellStyle: CellStyle = workbook.createCellStyle()

        //Apply cell color
        val colorMap: IndexedColorMap = (workbook as XSSFWorkbook).stylesSource.indexedColors
        var color = XSSFColor(IndexedColors.GREEN, colorMap).indexed
        cellStyle.fillForegroundColor = color
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND)

        //Apply font style on cell text
        val whiteFont = workbook.createFont()
        color = XSSFColor(IndexedColors.WHITE, colorMap).indexed
        whiteFont.color = color
        whiteFont.bold = true
        cellStyle.setFont(whiteFont)


        return cellStyle
    }

    private fun addDataIdentitas(rowIndex: Int, sheet: Sheet, pasienModel: PasienModel) {

        //Create row based on row index
        val row = sheet.createRow(rowIndex)

        //Add data to each cell
        createCell(row, 0, pasienModel.username) //Column 1
        createCell(row, 1, pasienModel.alamat) //Column 2
        createCell(row, 2, pasienModel.tanggalLahir) //Column 3
        createCell(row, 3, pasienModel.lamaDiagnosaDm) //Column 3
        createCell(row, 4, pasienModel.lamaDiagnosaDm) //Column 3
        createCell(row, 5, pasienModel.pengobatan) //Column 3
        createCell(row, 6, pasienModel.pendamping) //Column 3
    }

    private fun createCell(row: Row, columnIndex: Int, value: String?) {
        val cell = row.createCell(columnIndex)
        cell?.setCellValue(value)
    }

    fun createExcel(workbook: Workbook) {

        //Get App Director, APP_DIRECTORY_NAME is a string
        val appDirectory = activity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)

        //Check App Directory whether it exists or not, create if not.
        if (appDirectory != null && !appDirectory.exists()) {
            appDirectory.mkdirs()
        }

        //Create excel file with extension .xlsx
        val excelFile = File(path, "data.xlsx")

        //Write workbook to file using FileOutputStream

        try {
            val fileOut = FileOutputStream(excelFile)
            workbook.write(fileOut)
            fileOut.close()
            showToast(activity, "Berhasil membuat file Di folder Document/test.xlsx")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            showLogAssert("error", "${e.printStackTrace()}")
            showToast(activity, "Gagal membuat file")
        } catch (e: IOException) {
            e.printStackTrace()
            showLogAssert("error", "${e.printStackTrace()}")
            showToast(activity, "Gagal membuat file 1")
        }
    }
}