package com.example.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModelsImpl
import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.MovieVO

class MovieDetailsViewModel : ViewModel() {
    //Model
    private val mMovieModel = MovieModelsImpl

    //Live Data
    var movieDetailsLiveData: LiveData<MovieVO?>? = null
    val castLiveData = MutableLiveData<List<ActorsVO>>()
    val crewLiveData = MutableLiveData<List<ActorsVO>>()
    val mErrorLiveData = MutableLiveData<String>()

    fun getInitialData(movieId: Int) {
        movieDetailsLiveData = mMovieModel.getMovieDetails(movieId.toString(), onFailure = {
            mErrorLiveData.postValue(it)
        })

        mMovieModel.getCreditsByMovie(movieId.toString(),
            onSuccess = {
                castLiveData.postValue(it.first ?: listOf())
                crewLiveData.postValue(it.second ?: listOf())

            }, onFailure = {
                mErrorLiveData.postValue(it)
            })
    }

}