package com.example.themovieapp.network.responses

import com.example.themovieapp.data.vos.ActorsVO
import com.google.gson.annotations.SerializedName

data class GetCreditsByMovieResponse(
    @SerializedName("cast")
    val cast: List<ActorsVO>?,
    @SerializedName("crew")
    val crew: List<ActorsVO>?
)