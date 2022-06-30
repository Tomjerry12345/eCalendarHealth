package com.mybaseprojectandroid.utils.system

import android.os.Environment
import androidx.activity.ComponentActivity
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

class ExcellUtils(private val activity: ComponentActivity, val dataPasienModel: List<PasienModel>) {

    val path =
        Environment.getExternalStorageDirectory().path + "/" + Environment.DIRECTORY_DOCUMENTS

    fun createWorkbook(): Workbook {
        // Creating excel workbook
        val workbook = XSSFWorkbook()

        //Creating first sheet inside workbook
        //Constants.SHEET_NAME is a string value of sheet name
        val sheet: Sheet = workbook.createSheet("identitas")
        val sheet1: Sheet = workbook.createSheet("aktivitas")
        val sheet2: Sheet = workbook.createSheet("pemeriksaan")

        //Create Header Cell Style
        val cellStyle = getHeaderStyle(workbook)

        //Creating sheet header row
        createSheetHeader(cellStyle, sheet)
        createSheetHeader(cellStyle, sheet1)
        createSheetHeader(cellStyle, sheet2)

        //Adding data to the sheet
        var i = 1
        dataPasienModel.forEach {
            addData(i, sheet, it)
            i += 1
        }

        var j = 1
        dataPasienModel.forEach {
            addData(j, sheet1, it)
            j += 1
        }

        var k = 1
        dataPasienModel.forEach {
            addData(k, sheet2, it)
            k += 1
        }

        return workbook
    }

    private fun createSheetHeader(cellStyle: CellStyle, sheet: Sheet) {
        //setHeaderStyle is a custom function written below to add header style

        //Create sheet first row
        val row = sheet.createRow(0)

        //Header list
        val HEADER_LIST = listOf(
            "username",
            "alamat",
            "umur",
            "tanggal_lahir",
            "lama_terdiagnosa",
            "pengobatan",
            "pendamping",
        )

        //Loop to populate each column of header row
        for ((index, value) in HEADER_LIST.withIndex()) {

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

    private fun addData(rowIndex: Int, sheet: Sheet, pasienModel: PasienModel) {

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