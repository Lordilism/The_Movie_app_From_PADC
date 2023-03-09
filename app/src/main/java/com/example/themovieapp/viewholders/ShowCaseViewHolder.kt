package com.example.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.example.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_showcase.view.*

class ShowCaseViewHolder(itemView: View,private val mDelegate: ShowCaseViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
     private var mMovieVO: MovieVO? = null
    init {
        itemView.setOnClickListener {
            mMovieVO?.let {
                mDelegate.onTapMovieFromShowcase(it.id)
            }
        }
    }
    fun bindData(movie:MovieVO){
        mMovieVO = movie
        Glide.with(itemView.context)
            .load("${IMAGE_BASE_URL}${movie.posterPath}")
            .into(itemView.ivShowcase)

        itemView.tvShowCaseMovieName.text = movie.title
        itemView.tvShowCaseMovieDate.text = movie.releaseDate
    }
}