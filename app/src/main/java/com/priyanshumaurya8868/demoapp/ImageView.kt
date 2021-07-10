package com.priyanshumaurya8868.demoapp

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(uri: String) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(this).load(uri).apply(requestOptions).placeholder(R.drawable.ic_businessman_280)
        .into(this)
}