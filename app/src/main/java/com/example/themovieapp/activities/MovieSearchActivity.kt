package com.example.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovieapp.R
import com.example.themovieapp.adapters.MovieAdapter
import com.example.themovieapp.data.models.MovieModels
import com.example.themovieapp.data.models.MovieModelsImpl
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_search.*
import java.util.concurrent.TimeUnit

class MovieSearchActivity : AppCompatActivity(), MovieViewHolderDelegate {
    //Adapter
    private lateinit var mMovieAdapter: MovieAdapter

    //model
    private val mMovieModels: MovieModels = MovieModelsImpl

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MovieSearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)
        setUpRecyclerView()
        setUpListeners()
    }

    private fun setUpListeners() {
        etSearch.textChanges()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .flatMap {
                mMovieModels.searchMovie(it.toString())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mMovieAdapter.setNewData(it)
            }, {
                showError(it.localizedMessage ?: "")
            })
    }

    private fun showError(localizedMessage: String?) {
        Toast.makeText(this, localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun setUpRecyclerView() {
        mMovieAdapter = MovieAdapter(this)
        rvMovieFromSearch.adapter = mMovieAdapter
        rvMovieFromSearch.layoutManager = GridLayoutManager(this, 2)

    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId))
    }
}