package com.movie.six.coding.test

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.support.v4.view.ViewPager




class ThumbnailsSliderAdapter(context: Context, urlList: ArrayList<String>): PagerAdapter() {

    private val context: Context = context
    private lateinit var layoutInflater: LayoutInflater
    private var urlList = urlList

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        if (urlList == null)
            return 0
        else
            return urlList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any{
        layoutInflater = LayoutInflater.from(container.context)
        val view = layoutInflater.inflate(R.layout.view_pager_image, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        Glide.with(context).load(urlList[position]).into(imageView)
        container.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}