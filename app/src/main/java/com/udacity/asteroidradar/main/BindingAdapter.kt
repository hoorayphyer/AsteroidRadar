package com.udacity.asteroidradar.main

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImagePictureOfDay(imgView : ImageView, imgUrl : String?) {
    imgUrl?.let{
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context).load(imgUri).into(imgView)
    }
}