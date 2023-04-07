package com.example.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModels
import com.example.themovieapp.data.models.MovieModelsImpl
import com.example.themovieapp.mvp.views.MovieDetailsView

class MovieDetailsPresenterImpl : ViewModel(), MovieDetailsPresenter {
    var mView: MovieDetailsView? = null

    private val mMovieModel: MovieModels = MovieModelsImpl
    override fun initView(view: MovieDetailsView) {
        mView = view
    }

    override fun onUiReadyInMovieDetails(owner: LifecycleOwner, movieId: Int) {
        //Movie Details
        mMovieModel.getMovieDetails(movieId.toString(), onFailure = {
            mView?.showError(it)
        })?.observe(owner) {
            mView?.showMovieDetails(it)
        }

        //Credits By Movies
        mMovieModel.getCreditsByMovie(movieId.toString(), onSuccess = {
            mView?.showCreditsByMovie(it.first, it.second)
        }, onFailure = {
            mView?.showError(it)
        })
    }

    override fun onTapBack() {
        mView?.navigateBack()

    }

    override fun onUiReady(owner: LifecycleOwner) {

    }
}