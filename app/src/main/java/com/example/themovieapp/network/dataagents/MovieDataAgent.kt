package com.example.themovieapp.network.dataagents

import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO

interface MovieDataAgent {
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
    fun getMoviesByGenre(
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
        onSuccess:(Pair <List<ActorsVO>, List<ActorsVO>>) -> Unit,
        onFailure: (String) -> Unit
    )
}