package com.example.themovieapp.data.models

import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO

interface MovieModels {
    fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getPopularMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getGenres(
        onSuccess: (List<GenreVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getMoviesByGenres(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getActors(
        onSuccess: (List<ActorsVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getCreditsByMovie(
        movieId: String,
        onSuccess:(Pair<List<ActorsVO>,List<ActorsVO>>) -> Unit,
        onFailure: (String) -> Unit
    )
}