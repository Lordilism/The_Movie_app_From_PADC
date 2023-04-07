package com.example.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.themovieapp.data.models.MovieModels
import com.example.themovieapp.data.models.MovieModelsImpl
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.interactors.MovieInteractor
import com.example.themovieapp.interactors.MovieInteractorImpl
import com.example.themovieapp.mvp.views.MainView

class MainPresenterImpl : ViewModel(), MainPresenter {
    //view
    var mView: MainView? = null

    //Presenter
    private val mMovieInteractor: MovieInteractor = MovieInteractorImpl()

    //states
    private var mGenres: List<GenreVO>? = listOf()

    override fun initView(view: MainView) {
        mView = view
    }

    override fun onTapGenre(genrePosition: Int) {
        mGenres?.getOrNull(genrePosition)?.id?.let { genreId ->
            mMovieInteractor.getMoviesByGenres(genreId.toString(), onSuccess = {
                mView?.showMoviesByGenre(it)
            }, onFailure = {
                mView?.showError(it)
            })

        }
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onTapMovie(movieId: Int) {
        mView?.navigateToMovieDetailsScreen(movieId)
    }

    override fun onUiReady(owner: LifecycleOwner) {
        //Now Playing
        mMovieInteractor.getNowPlayingMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showNowPlayingMovies(it)
        }

        //Popular
        mMovieInteractor.getPopularMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showPopularMovies(it)
        }

        //Top Rated Movies
        mMovieInteractor.getTopRatedMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showTopRatedMovies(it)
        }

        //Genre and Get Movie For First Genre
        mMovieInteractor.getGenres(
            onSuccess = {
                mGenres = it
                mView?.showGenres(it)
                it.firstOrNull()?.id?.let { firstGenreId ->
                    onTapGenre(firstGenreId)
                }
            }, onFailure = {
                mView?.showError(it)
            }
        )

        //Actors
        mMovieInteractor.getActors(
            onSuccess = {
                mView?.showActors(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }
}