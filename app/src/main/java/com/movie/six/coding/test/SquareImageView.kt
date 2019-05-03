package com.movie.six.coding.test

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class SquareImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight

        if (width > height)
            setMeasuredDimension(height, height)
        else
            setMeasuredDimension(width, width)

    }
}