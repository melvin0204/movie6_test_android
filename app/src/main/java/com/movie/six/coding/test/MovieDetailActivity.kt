package com.movie.six.coding.test

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_movie_detail.*
import android.widget.LinearLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieID: String
    private var movieObject: MovieObject? = null
    private lateinit var adapter:ThumbnailsSliderAdapter
    private var dotCounts: Int = 0
    private var dots: ArrayList<ImageView> = arrayListOf()
    private var ratingStars: ArrayList<ImageView> = arrayListOf()
    private var urlList: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        movieID = intent.getStringExtra("MovieID")
        httpGetJson()
    }

    @SuppressLint("SetTextI18n")
    fun httpGetJson() {
        try {
            Fuel.get("https://api.hkmovie6.com/hkm/movies/" + movieID).responseString{ request, response, result ->
                runOnUiThread {
                    Log.i(MainActivity.TAG, result.get())
                    var httpResponse:String = result.get()
                    movieObject = Gson().fromJson(httpResponse,MovieObject::class.java)

                    val trailerUrl = movieObject!!.trailerUrl
                    var splitUrl = trailerUrl.split("v=","&")
                    if (splitUrl.size >= 2){
                        urlList.add("https://img.youtube.com/vi/"+splitUrl[1]+"/0.jpg")
                    }
                    val multitrailers = movieObject!!.multitrailers
                    for (each in multitrailers){
                        splitUrl = each.split("v=","&")
                        if (splitUrl.size >= 2){
                            urlList.add("https://img.youtube.com/vi/"+splitUrl[1]+"/0.jpg")
                        }
                    }
                    val screenShots = movieObject!!.screenShots
                    for (each in screenShots){
                        urlList.add(each)
                    }
                    adapter = ThumbnailsSliderAdapter(this, urlList)
                    viewPager.adapter = adapter
                    dotCounts = adapter.count
                    if (dotCounts > 1){
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins (8, 0, 8, 0)
                        dots.add(ImageView(this))
                        dots[0].setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.active_dot))
                        sliderDots.addView(dots[0], params)
                        for (i in 1 until (dotCounts)){
                            dots.add(ImageView(this))
                            dots[i].setImageDrawable(
                                ContextCompat.getDrawable(
                                    applicationContext,
                                    R.drawable.inactive_dot
                                )
                            )

                            sliderDots.addView(dots[i], params)
                        }
                    }
                    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                        override fun onPageSelected(position: Int) {
                            for (i in 0 until dotCounts) {
                                dots[i].setImageDrawable(
                                    ContextCompat.getDrawable(
                                        applicationContext,
                                        R.drawable.inactive_dot
                                    )
                                )
                            }
                            Log.i(TAG, "position: "+position)
                            if(position < dotCounts){
                                dots[position].setImageDrawable(
                                    ContextCompat.getDrawable(
                                        applicationContext,
                                        R.drawable.active_dot
                                    )
                                )
                            }
                        }

                        override fun onPageScrollStateChanged(state: Int) {}
                    })

                    val rating = Math.round(movieObject!!.rating/10.0)/10.0
                    rating_tw.text = rating.toString()
                    val params = LinearLayout.LayoutParams(35, 35)
                    params.setMargins (5, 0, 5, 0)
                    for (i in 0 until 5){
                        ratingStars.add(ImageView(this))
                        if(rating >= i + 1){
                            ratingStars[i].setImageDrawable(
                                ContextCompat.getDrawable(
                                    applicationContext,
                                    R.drawable.full_star_rating
                                )
                            )
                        }else{
                            if(rating > i){
                                ratingStars[i].setImageDrawable(
                                    ContextCompat.getDrawable(
                                        applicationContext,
                                        R.drawable.half_star_rating
                                    )
                                )
                            }else{
                                ratingStars[i].setImageDrawable(
                                    ContextCompat.getDrawable(
                                        applicationContext,
                                        R.drawable.empty_star_rating
                                    )
                                )
                            }
                        }
                        star_rating_layout.addView(ratingStars[i], params)
                    }

                    movie_name_tw.text = movieObject!!.chiName
                    like_tw.text = movieObject!!.favCount.toString()
                    comment_tw.text = movieObject!!.commentCount.toString()

                    val parser = SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss", Locale.US)
                    val dateObj = parser.parse(movieObject!!.openDate)
                    var calendar = Calendar.getInstance()
                    calendar.time = dateObj
                    val duration = movieObject!!.duration.toString()
                    val category = movieObject!!.chiInfoDict.級別
                    other_info_tw.text = calendar.get(Calendar.YEAR).toString() + "年" + calendar.get(Calendar.MONTH).toString() + "月" + calendar.get(Calendar.DATE).toString() + "日" + " | " + duration + "分鐘 | " + category

                    movie_describe_tw.text = movieObject!!.chiSynopsis
                    director_tw.text = movieObject!!.chiInfoDict.導演
                    actor_tw.text = movieObject!!.chiInfoDict.演員
                    genre_tw.text = movieObject!!.chiInfoDict.類型
                    language_tw.text = movieObject!!.chiInfoDict.語言


                }
            }
        } catch (e: Exception) {
            Log.e(MainActivity.TAG, e.message)
        } finally {

        }
    }

    companion object {
        val TAG = "MovieDetailActivity"
    }
}
