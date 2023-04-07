package com.example.themovieapp.mvp.views

import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO

interface MainView: BaseView {
    fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>)
    fun showPopularMovies(popularMovies: List<MovieVO>)
    fun showTopRatedMovies( topRatedMovies: List<MovieVO>)
    fun showGenres( genreList: List<GenreVO>)
    fun showMoviesByGenre( movieByGenre: List<MovieVO>)
    fun showActors(actors: List<ActorsVO>)
    fun navigateToMovieDetailsScreen(movieId : Int)
}