package com.mybaseprojectandroid.utils.system

import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.FragmentActivity

class FilesUtils {
    // Request code for creating a PDF document.
    val CREATE_FILE = 1

    fun createFile(
        resultLauncher: ActivityResultLauncher<Intent>,
        name: String,
        requireActivity: FragmentActivity
    ) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.ms-excel"
            putExtra(Intent.EXTRA_TITLE, name)
        }
        resultLauncher.launch(intent)
//        activity.startActivityForResult(intent, CREATE_FILE)
    }
}