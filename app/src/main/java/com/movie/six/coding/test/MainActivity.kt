package com.movie.six.coding.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
        private var movieList: Array<MovieObject> = arrayOf<MovieObject>()
        private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout.setOnRefreshListener {
            httpGetJson()
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        adapter = CustomAdapter(this, movieList)
        layoutManager = GridLayoutManager(this, 3)

        var scrollPosition = 0

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as GridLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }


        with(recyclerView) {
            layoutManager = this@MainActivity.layoutManager
            scrollToPosition(scrollPosition)
        }

        recyclerView.adapter = adapter

    }

    fun httpGetJson() {
        try {
            Fuel.get("https://api.hkmovie6.com/hkm/movies?type=showing").responseString{ request, response, result ->
                runOnUiThread {
                    Log.i(TAG, result.get())
                    if (swipeRefreshLayout.isRefreshing){
                        swipeRefreshLayout.isRefreshing = false
                    }
                    var httpResponse:String = result.get()
                    adapter.movieList = Gson().fromJson(httpResponse,Array<MovieObject>::class.java)
                    adapter.notifyDataSetChanged()
                    hint_tw.visibility = View.GONE
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        } finally {

        }
    }

    companion object {
        val TAG = "MainActivity"
    }
}
