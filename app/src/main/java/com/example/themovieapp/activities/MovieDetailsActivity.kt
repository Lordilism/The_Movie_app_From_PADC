package com.example.themovieapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.themovieapp.R
import com.example.themovieapp.data.models.MovieModels
import com.example.themovieapp.data.models.MovieModelsImpl
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.utils.IMAGE_BASE_URL
import com.example.themovieapp.viewpods.ActorListViewPods
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    companion object{
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun newIntent(context: Context,movieId: Int):Intent{
            val intent = Intent(context,MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID,movieId)
            return intent
        }
    }

    private val mMovieModels: MovieModels = MovieModelsImpl

    private lateinit var actorsViewPod : ActorListViewPods
    private lateinit var creatorsViewPod : ActorListViewPods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)



        setUpViewPods()
        setUpListeners()

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID,0)
        movieId?.let {
            requestData(it)
        }
    }

    private fun requestData(movieId: Int){
        mMovieModels.getMovieDetails(
            movieId = movieId.toString(),
            onFailure = {
                showError(it)
            }

        )?.observe(this){
            bindData(it)
        }
        mMovieModels.getCreditsByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                actorsViewPod.setData(it.first)
                creatorsViewPod.setData(it.second)
            },
            onFailure = {

            }

        )
    }

    private fun showError(failure: String) {
        Toast.makeText(this,failure,Toast.LENGTH_SHORT).show()
    }

    private fun setUpListeners(){
        btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }
    private fun setUpViewPods(){
        actorsViewPod = vpActors as ActorListViewPods
        actorsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_actors),
            moreTitleText = ""
        )
        creatorsViewPod = vpCreators as ActorListViewPods
        creatorsViewPod.setUpActorViewPod(
            backgroundColorReference = R.color.colorPrimary,
            titleText = getString(R.string.lbl_creators),
            moreTitleText = getString(R.string.lbl_more_creators)
        )
    }

    private fun bindData(movie: MovieVO){
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.backDropPath}")
            .into(ivMovieDetails)
        tvMovieName.text = movie.title ?: ""
        tvMovieReleaseYear.text = movie.releaseDate?.substring(0,4)
        tvRating.text = movie.voteAverage?.toString() ?: ""
        movie.voteCount?.let {
            tvNumbersOfVotes.text = "$it VOTES"
        }
        rbRatingMovieDetails.rating = movie.getRatingBasedOnFiveStars()

        bindGenres(movie, movie.genres ?: listOf())

        tvOverview.text = movie.overview ?: ""
        tvOriginalTitle.text = movie.title ?: ""
        tvType.text = movie.getGenresAsCommaSeparatedString()
        tvProduction.text = movie.getProductionCountriesAsCommaSeparatedString()
        tvPremiere.text = movie.releaseDate ?: ""
        tvDescription.text = movie.overview ?: ""
    }
    private fun bindGenres(
        movie: MovieVO,
        genres: List<GenreVO>
    ){
        movie.genres?.count()?.let {
            tvFirstGenre.text = genres.firstOrNull()?.name ?: ""
            tvSecondGenre.text = genres.getOrNull(1)?.name ?: ""
            tvThirdGenre.text = genres.getOrNull(2)?.name ?: ""

            if (it < 3){
                tvThirdGenre.visibility = View.GONE
            }else if (it < 2){
                tvSecondGenre.visibility = View.GONE
            }
        }
    }
}