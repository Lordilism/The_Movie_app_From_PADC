package com.example.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModelsImpl
import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO

class MainViewModel : ViewModel() {
    //Model
    private val mMovieModel = MovieModelsImpl

    //LiveData
    var nowPlayingMovieLiveData: LiveData<List<MovieVO>>? = null
    var popularMovieLiveData: LiveData<List<MovieVO>>? = null
    var topRatedMovieLiveData: LiveData<List<MovieVO>>? = null
    val genreLiveData = MutableLiveData<List<GenreVO>>()
    val movieByGenreLiveData = MutableLiveData<List<MovieVO>>()
    val actorsLiveData = MutableLiveData<List<ActorsVO>>()
    val mErrorLiveData = MutableLiveData<String>()

    fun getInitialData() {
        nowPlayingMovieLiveData = mMovieModel.getNowPlayingMovies { mErrorLiveData.postValue(it) }
        popularMovieLiveData = mMovieModel.getPopularMovies { mErrorLiveData.postValue(it) }
        topRatedMovieLiveData = mMovieModel.getTopRatedMovies { mErrorLiveData.postValue(it) }

        mMovieModel.getGenres(
            onSuccess = {
                genreLiveData.postValue(it)
                getMovieByGenre(0)
            },
            onFailure = {
                mErrorLiveData.postValue(it)
            }
        )

        mMovieModel.getActors(
            onSuccess = {
                actorsLiveData.postValue(it)
            },
            onFailure = {
                mErrorLiveData.postValue(it)
            }
        )
    }

    fun getMovieByGenre(genrePosition: Int) {

        genreLiveData.value?.getOrNull(genrePosition)?.id?.let {
            mMovieModel.getMoviesByGenres(it.toString(),
                onSuccess = {
                    movieByGenreLiveData.postValue(it)
                },
                onFailure = {
                    mErrorLiveData.postValue(it)
                })
        }

    }


}