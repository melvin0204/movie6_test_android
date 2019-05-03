package com.movie.six.coding.test

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_list_item.*




/**
 * Provide views to RecyclerView with data from dataSet.
 *
 * Initialize the dataset of the Adapter.
 *
 * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
 */
class CustomAdapter(context:Context, private val movieSet: Array<MovieObject>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var movieList:Array<MovieObject> = movieSet
    val context = context

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var movie_poster: ImageView
        var movie_name: TextView
        var rating_tw: TextView
        var like_tw: TextView
        var comment_tw: TextView

        init {
            v.setOnClickListener {
                Log.d(TAG, "Element $adapterPosition clicked.")
                val intent = Intent(context,MovieDetailActivity::class.java)
                intent.putExtra("MovieID", movieList[adapterPosition].id.toString())
                context.startActivity(intent)
            }

            movie_poster = v.findViewById(R.id.movie_poster)
            movie_name = v.findViewById(R.id.movie_name)
            rating_tw = v.findViewById(R.id.rating_tw)
            like_tw = v.findViewById(R.id.like_tw)
            comment_tw = v.findViewById(R.id.comment_tw)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_list_item, viewGroup, false)

        val height = viewGroup.measuredHeight / 3
        v.layoutParams.height = height

        return ViewHolder(v)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        Glide.with(viewHolder.itemView).load(movieList[position].thumbnail).into(viewHolder.movie_poster)
        viewHolder.movie_name.text = movieList[position].chiName
        viewHolder.rating_tw.text = String.format("%.1f",movieList[position].rating/100.0)
        viewHolder.like_tw.text = movieList[position].favCount.toString()
        viewHolder.comment_tw.text = movieList[position].commentCount.toString()

    }

    override fun getItemCount() = movieList.size

    fun updateMovie(updateMovieList:Array<MovieObject>){
        movieList = updateMovieList
        notifyDataSetChanged()
    }

    companion object {
        private val TAG = "CustomAdapter"
    }
}
