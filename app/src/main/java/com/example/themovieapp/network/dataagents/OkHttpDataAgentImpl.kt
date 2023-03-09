package com.example.themovieapp.network.dataagents

import android.os.AsyncTask
import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.network.responses.MovieListResponse
import com.example.themovieapp.utils.API_GET_NOW_PLAYING
import com.example.themovieapp.utils.BASE_URL
import com.example.themovieapp.utils.MOVIE_API_KEY
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.nio.channels.AsynchronousByteChannel
import java.util.concurrent.TimeUnit

object OkHttpDataAgentImpl:MovieDataAgent {

    private val mClient:OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15,TimeUnit.SECONDS)
        .readTimeout(15,TimeUnit.SECONDS)
        .writeTimeout(15,TimeUnit.SECONDS)
        .build()

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        GetNowPlayingMovieOkHttpTask(mClient).execute()
    }

    override fun getPopularMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit,
    ) {

    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit,
    ) {

    }

    override fun getActors(onSuccess: (List<ActorsVO>) -> Unit, onFailure: (String) -> Unit) {

    }

    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit,
    ) {

    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorsVO>, List<ActorsVO>>) -> Unit,
        onFailure: (String) -> Unit,
    ) {

    }

    class GetNowPlayingMovieOkHttpTask(
        private val mOkHttpClient: OkHttpClient
    ):
            AsyncTask<Void,Void,MovieListResponse>(){
        override fun doInBackground(vararg params: Void?): MovieListResponse? {
            val request = Request.Builder()
                .url("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")
                .build()

            try {
                val response = mOkHttpClient.newCall(request).execute()

                if(response.isSuccessful){
                    response.body?.let {
                        val responseString = it.string()
                        val response = Gson().fromJson(responseString,MovieListResponse::class.java)
                        return response
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(allNewsResponse: MovieListResponse?) {
            super.onPostExecute(allNewsResponse)
        }
    }
}