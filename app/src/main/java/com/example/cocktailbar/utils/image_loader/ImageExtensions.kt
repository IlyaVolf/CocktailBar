package com.example.cocktailbar.utils.image_loader

import android.widget.ImageView
import com.example.cocktailbar.R
import com.example.cocktailbar.utils.ResourcesUtils

fun ImageView.loadImage(url: String) {
    ImageLoader
        .load(url)
        .error(R.drawable.image_placeholder)
        .placeholder(R.drawable.image_placeholder)
       // .centerCrop()
        .roundedCorners(ResourcesUtils.getPxByDp(4f))
        .into(this)
}
