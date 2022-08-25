package com.mybaseprojectandroid.utils.other

import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.model.CardItem

object Constant {

    // mime type
    const val MIME_ALL_IMAGE = "image/*"
    const val MIME_ALL_DOCUMENT = "application/*"
    const val MIME_PDF = "application/pdf"

    // saved data
    const val examplesKeySavedDataString = "examples"

    // retrofit base url
    const val BASE_URL = "http://192.168.1.16:4000/"

    // list dropdown examples
    val exampleListDropwdownText = listOf("dropdown 1", "dropdown 2")

    const val KEY_PASIEN = "pasien"
    const val KEY_ADMIN = "admin"
    const val KEY_AKTIVITAS = "aktivitas"
    const val KEY_DATE_BRING_WALKING = "bring-walking"
    const val KEY_PEMERIKSAAN = "pemeriksaan"
    const val KEY_IS_LOGGIN = "isLoggin"
    const val KEY_MESSAGE_NOTIF_CONTENT = "message-notif-content"
    const val KEY_MESSAGE_NOTIF_TITLE = "message-notif-title"

    const val KEY_NAMA_LENGKAP = "nama-lengkap"
    const val KEY_ID_USER = "id-user"
    const val KEY_PERSEN = "persen-user"
    const val KEY_IS_READING_DOCUMENT = "isReadingDocument"

    val listCardItem = listOf(
        CardItem(
            R.drawable.item_card1,
            "Aktivitas",
            "Berisi panduan melakukan aktivitas brisk walking",
            R.drawable.card1
        ),
        CardItem(
            R.drawable.item_card2,
            "Edukasi",
            "Pengetahuan dasar tentang Diabetes Melitus",
            R.drawable.card2
        ),
        CardItem(
            R.drawable.item_card3,
            "Konsultasi",
            "Konsultasi langsung dengan perawat dan mendapatkan dukungan dari sesama pengguna",
            R.drawable.card3
        ),
        CardItem(
            R.drawable.item_card4,
            "Pemeriksaan",
            "Masukkan hasil pemeriksaan gula darah anda",
            R.drawable.card4
        )
    )

    const val START = 0
    const val END = 5
}