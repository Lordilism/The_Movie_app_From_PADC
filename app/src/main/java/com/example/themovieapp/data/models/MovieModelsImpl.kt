package com.example.themovieapp.data.models

import android.content.Context
import com.example.themovieapp.data.vos.*
import com.example.themovieapp.network.dataagents.MovieDataAgent
import com.example.themovieapp.network.dataagents.RetrofitDataAgentImpl
import com.example.themovieapp.persistence.MovieDatabase

object MovieModelsImpl : MovieModels {
    //Data Agent
    private val mMovieDataAgent: MovieDataAgent = RetrofitDataAgentImpl

    //Database
    private var mMovieDatabase: MovieDatabase? = null

    fun initDatabase(context: Context) {
        mMovieDatabase = MovieDatabase.getDBInstance(context)
    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        //Database
        onSuccess(mMovieDatabase?.movieDao()?.getMovieByType(type = NOW_PLAYING) ?: listOf())
        //Network
        mMovieDataAgent.getNowPlayingMovies(onSuccess = {
            it.forEach { movie -> movie.type = NOW_PLAYING }
            mMovieDatabase?.movieDao()?.insertMovies(it)
            onSuccess(it)
        }, onFailure = onFailure)
    }

    override fun getPopularMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {

        onSuccess(mMovieDatabase?.movieDao()?.getMovieByType(type = POPULAR) ?: listOf())
        mMovieDataAgent.getPopularMovies(onSuccess = {
            it.forEach { movie -> movie.type = POPULAR }
            mMovieDatabase?.movieDao()?.insertMovies(it)
            onSuccess(it)
        }, onFailure = onFailure)
    }

    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        onSuccess(mMovieDatabase?.movieDao()?.getMovieByType(type = TOP_RATED) ?: listOf())

        mMovieDataAgent.getTopRatedMovies(onSuccess = {
            it.forEach { movie -> movie.type = TOP_RATED }
            mMovieDatabase?.movieDao()?.insertMovies(it)
            onSuccess(it)
        }, onFailure = onFailure)
    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieDataAgent.getGenres(onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getMoviesByGenres(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        mMovieDataAgent.getMoviesByGenre(
            genreId = genreId,
            onSuccess = onSuccess,
            onFailure = onFailure)
    }

    override fun getActors(onSuccess: (List<ActorsVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieDataAgent.getActors(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        //Database
        val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
        movieFromDatabase?.let {
            onSuccess(it)
        }
        //Network
        mMovieDataAgent.getMovieDetails(
            movieId = movieId,
            onSuccess = {
                val movieFromDatabase =
                    mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
                it.type = movieFromDatabase?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)
                onSuccess(it)
            },
            onFailure = onFailure
        )
    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorsVO>, List<ActorsVO>>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        mMovieDataAgent.getCreditsByMovie(
            movieId = movieId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}