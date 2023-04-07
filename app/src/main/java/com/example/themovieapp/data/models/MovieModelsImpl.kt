package com.example.themovieapp.data.models

import androidx.lifecycle.LiveData
import com.example.themovieapp.data.vos.*
import com.example.themovieapp.utils.MOVIE_API_KEY
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers.io

object MovieModelsImpl : MovieModels, BaseModel() {
    //Data Agent
//    private val mMovieDataAgent: MovieDataAgent = RetrofitDataAgentImpl

    //Database
//    private var mMovieDatabase: MovieDatabase? = null

//    fun initDatabase(context: Context) {
//        mMovieDatabase = MovieDatabase.getDBInstance(context)
//    }


    override fun getNowPlayingMovies(

        onFailure: (String) -> Unit,
    ): LiveData<List<MovieVO>>? {
        //Database
//        onSuccess(mMovieDatabase?.movieDao()?.getMovieByType(type = NOW_PLAYING) ?: listOf())
        //Network
        mMovieApi.getNowPlayingMovies()
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = NOW_PLAYING }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())

            }, {
                onFailure(it.localizedMessage ?: "")
            }, {

            })
        return mMovieDatabase?.movieDao()?.getMovieByType(type = NOW_PLAYING)
    }

    override fun getPopularMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {

//        onSuccess(mMovieDatabase?.movieDao()?.getMovieByType(type = POPULAR) ?: listOf())
        mMovieApi.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = POPULAR }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            }, {
                //Complete Event
            })
        return mMovieDatabase?.movieDao()?.getMovieByType(type = POPULAR)
    }

    override fun getTopRatedMovies(
        onFailure: (String) -> Unit,
    ): LiveData<List<MovieVO>>? {
//        onSuccess(mMovieDatabase?.movieDao()?.getMovieByType(type = TOP_RATED) ?: listOf())

        mMovieApi.getTopRatedMovies(MOVIE_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie -> movie.type = TOP_RATED }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())

            }, {
                onFailure(it.localizedMessage ?: "")
            }, {

            })
        return mMovieDatabase?.movieDao()?.getMovieByType(type = TOP_RATED)
    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getGenres(MOVIE_API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.genres?.let { genre ->
                    onSuccess(genre)
                }

            }, {
                onFailure(it.localizedMessage ?: "")
            }, {

            })
    }

    override fun getMoviesByGenres(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit,
    ) {
        mMovieApi.getMoviesByGenre(genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getActors(onSuccess: (List<ActorsVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getActors()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieDetails(
        movieId: String,
        onFailure: (String) -> Unit,
    ): LiveData<MovieVO?>? {
        //Database
//        val movieFromDatabase = mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
//        movieFromDatabase?.let {
//            onSuccess(it)
//        }
        //Network
        mMovieApi.getMovieDetails(movieId)
            .subscribeOn(io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val movieFromToSync =
                    mMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId.toInt())
                it.type = movieFromToSync?.type
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)

            }, {
                onFailure(it.localizedMessage ?: "")
            })
        return mMovieDatabase?.movieDao()?.getMovieById(movieId.toInt())
    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorsVO>, List<ActorsVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getCreditsByMovie(movieId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun searchMovie(query: String): Observable<List<MovieVO>> {
        return mMovieApi.searchMovie(query = query)
            .map {
                it.results ?: listOf()
            }
            .onErrorResumeNext { Observable.just(listOf()) }
            .subscribeOn(io())
    }
}