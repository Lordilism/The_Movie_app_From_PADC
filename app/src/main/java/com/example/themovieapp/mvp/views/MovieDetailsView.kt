package com.example.themovieapp.mvp.views

import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.MovieVO

interface MovieDetailsView:BaseView {
    fun showMovieDetails(movie: MovieVO)
    fun showCreditsByMovie(cast: List<ActorsVO>, crew: List<ActorsVO>)
    fun navigateBack()
}