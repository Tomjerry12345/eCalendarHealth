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
    const val examplesKeySavedDataObject = "examples"

    // retrofit base url
    const val BASE_URL = "http://192.168.1.16:4000/"

    // list dropdown examples
    val exampleListDropwdownText = listOf("dropdown 1", "dropdown 2")

    const val KEY_PASIEN = "pasien"
    const val KEY_AKTIVITAS = "aktivitas"
    const val KEY_PEMERIKSAAN = "pemeriksaan"
    const val KEY_IS_LOGGIN = "isLoggin"

    val listCardItem = listOf(
        CardItem(R.drawable.item_card1,"Aktivitas","Lorem ipsum dolor sit consectetur adipis", R.drawable.card1),
        CardItem(R.drawable.item_card2,"Edukasi","Lorem ipsum dolor sit consectetur adipis",R.drawable.card2),
        CardItem(R.drawable.item_card3,"Konsultasi","Lorem ipsum dolor sit consectetur adipis",R.drawable.card3),
        CardItem(R.drawable.item_card4,"Pemeriksaan","Lorem ipsum dolor sit consectetur adipis",R.drawable.card4)
    )
}