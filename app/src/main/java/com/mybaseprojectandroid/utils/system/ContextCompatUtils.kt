package com.mybaseprojectandroid.utils.system

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun getDrawable(context: Context, id: Int): Drawable? {
    return ContextCompat.getDrawable(context, id)
}

fun getColor(context: Context, id: Int): Int {
    return ContextCompat.getColor(context, id)
}