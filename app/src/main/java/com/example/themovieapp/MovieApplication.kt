package com.example.themovieapp

import android.app.Application
import com.example.themovieapp.data.models.MovieModelsImpl

class MovieApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        MovieModelsImpl.initDataBase(applicationContext)
    }
}