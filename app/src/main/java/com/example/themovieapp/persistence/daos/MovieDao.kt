package com.example.themovieapp.persistence.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themovieapp.data.vos.MovieVO

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleMovie(movie: MovieVO?)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<MovieVO>

    @Query("SELECT * FROM movies WHERE id= :movieId")
    fun getMovieById(movieId: Int):MovieVO?

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getMovieByType(type: String): List<MovieVO>

    @Query("DELETE FROM movies")
    fun deleteAllMovies()
}