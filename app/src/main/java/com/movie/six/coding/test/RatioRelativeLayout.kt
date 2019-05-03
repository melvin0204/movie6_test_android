package com.movie.six.coding.test

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class RatioRelativeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = width * 7/10

        setMeasuredDimension(width,height)
    }
}