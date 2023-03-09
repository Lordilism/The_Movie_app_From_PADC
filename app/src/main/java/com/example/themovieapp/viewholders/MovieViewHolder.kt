package com.example.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.example.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_movie.view.*

class MovieViewHolder(itemView: View,mDelegate: MovieViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
    private var mMovieVO: MovieVO? = null
    init {
        itemView.setOnClickListener {
            mMovieVO?.let {
                mDelegate.onTapMovie(it.id)
            }

        }
    }
    fun bindData(movie:MovieVO){
        mMovieVO = movie
        Glide.with(itemView.context)
            .load("${IMAGE_BASE_URL}${movie.posterPath}")
            .into(itemView.ivMovieImage)
        itemView.tvMovieName.text = movie.title
        itemView.tvMovieRating.text = movie.voteAverage?.toString()
        itemView.rbMovieRating.rating = movie.getRatingBasedOnFiveStars()
    }
}