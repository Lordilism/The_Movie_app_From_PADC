package com.example.themovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class SpokenLanguageVO(
    @SerializedName("english_name")
    val englishName: String?,
    @SerializedName("iso_631_1")
    val iso6311: String?,
    @SerializedName("name")
    val name: String?
)
