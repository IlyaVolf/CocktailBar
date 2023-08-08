package com.example.cocktailbar.presentation.views

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView


class SquareCardView(context: Context?, attrs: AttributeSet?) :
    MaterialCardView(context!!, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, ignoredHeightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}