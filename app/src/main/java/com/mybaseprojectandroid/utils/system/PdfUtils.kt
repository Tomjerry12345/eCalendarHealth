package com.mybaseprojectandroid.utils.system

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.mybaseprojectandroid.BuildConfig
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import java.io.*


class PdfUtils(private val activity: ComponentActivity) {

    val path =
        Environment.getExternalStorageDirectory().path + "/" + Environment.DIRECTORY_DOCUMENTS

    val PATH_DOCUMENT =
        "${activity.getExternalFilesDir(null)?.path}/${Environment.DIRECTORY_DOCUMENTS}"


    fun createBitmapFromLayout(v: View, width: Int, height: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        v.draw(canvas)
        return bitmap
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createPdf(bitmap: Bitmap) {
        val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(width, height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas

        val paint = Paint()

        canvas.drawPaint(paint)

        val bitmapNew: Bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)

        canvas.drawBitmap(bitmapNew, 0.toFloat(), 0.toFloat(), null)

        pdfDocument.finishPage(page)

        val file = File(path)

        try {
            createFile(Uri.fromFile(file))
        } catch (e: IOException) {
            e.printStackTrace()

            showToast(activity, "something wrong try again $e")

            pdfDocument.close()
            showToast(activity, "succesfull download")
            openPdf(path, "")
        }
    }

    val CREATE_FILE = 1

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }
        activity.startActivityForResult(intent, CREATE_FILE)
    }

    fun openPdfInRaw(path: String, name: String, raw: Int) {
        val file = File(path, name)
        copyFile(activity.resources.openRawResource(raw), FileOutputStream(file))
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri =
                FileProvider.getUriForFile(activity, "${BuildConfig.APPLICATION_ID}.provider", file)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            try {
                activity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showLogAssert("error", "No application for pdf view")
                showToast(activity, "No application for pdf view")
            }
        } else {
            showLogAssert("error", "path incorrect")
            showToast(activity, "path incorrect")
        }
    }

    fun openPdf(path: String, name: String) {
        val file = File(path, name)
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri =
                FileProvider.getUriForFile(activity, "${BuildConfig.APPLICATION_ID}.provider", file)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            try {
                activity.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showLogAssert("error", "No application for pdf view")
                showToast(activity, "No application for pdf view")
            }
        } else {
            showLogAssert("error", "path incorrect")
            showToast(activity, "path incorrect")
        }
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }

}
