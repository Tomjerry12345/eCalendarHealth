package com.mybaseprojectandroid.utils.other

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mybaseprojectandroid.R


@BindingAdapter("showImage")
fun showImage(imgView: ImageView, url: Int?) {
    Glide.with(imgView.context)
        .load(url)
        .placeholder(R.drawable.item_card1)
        .into(imgView)
}

@BindingAdapter("showImageUrl")
fun showImageUrl(imgView: ImageView, url: String?) {
    Glide.with(imgView.context)
        .load(url)
        .placeholder(R.drawable.item_card1)
        .into(imgView)
}

@BindingAdapter("showFirstChar")
fun showFirstChar(textView: TextView, url: String?) {
    textView.text = url?.get(0).toString()
}

@BindingAdapter("changeBg")
fun changeBg(imgView: ConstraintLayout, url: Int?) {
    if (url != null) {
        imgView.setBackgroundResource(url)
    }
}

@BindingAdapter("visibilityLine")
fun visibilityLine(view: View, url: Boolean?) {
    if (url == false) View.GONE.also { view.visibility = it } else view.visibility = View.VISIBLE
}

@BindingAdapter("switchPermission")
fun switchPermission(view: SwitchMaterial, value: Boolean?) {
//    if (url == false) View.GONE.also { view.visibility = it } else view.visibility = View.VISIBLE
    if (value != null) {
        view.isChecked = value
        view.isClickable = !value
    }
}