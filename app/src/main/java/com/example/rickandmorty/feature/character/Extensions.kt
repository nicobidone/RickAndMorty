package com.example.rickandmorty.feature.character

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rickandmorty.R

fun RequestManager.loadInto(image: String, view: ImageView) {
    this.load(image)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.character_image_placeholder)
        .into(view)
}