package com.example.themovieapp.network.dataagents

import android.os.AsyncTask
import android.util.Log
import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.network.responses.MovieListResponse
import com.example.themovieapp.utils.API_GET_NOW_PLAYING
import com.example.themovieapp.utils.BASE_URL
import com.example.themovieapp.utils.MOVIE_API_KEY
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

object MovieDataAgentImpl:MovieDataAgent {
    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
         GetNowPlayingMovieTask().execute()
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

    class GetNowPlayingMovieTask(): AsyncTask<Void,Void,MovieListResponse?>(){
        override fun doInBackground(vararg params: Void?): MovieListResponse? {

            //Create the HTTPUrlConnection
            val url:URL
            var reader:BufferedReader? = null
            val stringBuilder:StringBuilder

            try {
                url = URL("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")
                val connection = url.openConnection() as HttpURLConnection

                //Set HTTP Method
                connection.requestMethod = "GET"

                //give it 15seconds to response
                connection.readTimeout = 15 * 1000

                connection.doInput = true
                connection.doOutput = false

                connection.connect()

                reader = BufferedReader(InputStreamReader(connection.inputStream))
                stringBuilder = StringBuilder()

                for (line in reader.readLines()){
                    stringBuilder.append(line + "\n")
                }
                val responseString = stringBuilder.toString()
                Log.d("NowPlayingMovies",responseString)

                val movieListResponse = Gson().fromJson(responseString,MovieListResponse::class.java)
                return movieListResponse
            }catch (e:Exception){
                  e.printStackTrace()
                Log.e("NewsError",e.message ?: "")
            }finally {
                if (reader != null){
                    try {
                        reader.close()
                    }catch (ioe: Exception){
                        ioe.printStackTrace()
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: MovieListResponse?) {
            super.onPostExecute(result)
        }

    }
}