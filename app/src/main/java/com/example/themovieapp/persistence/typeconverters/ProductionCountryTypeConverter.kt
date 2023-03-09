package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.themovieapp.data.vos.ProductionCountriesVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductionCountryTypeConverter {
    @TypeConverter
    fun toString(productionCountry: List<ProductionCountriesVO>?): String{
        return Gson().toJson(productionCountry)
    }
    @TypeConverter
    fun toProductionCountry(productionCountryJsonString: String):List<ProductionCountriesVO>?{
        val productionCountryListType = object : TypeToken<List<ProductionCountriesVO>?>() {}.type
        return Gson().fromJson(productionCountryJsonString,productionCountryListType)
    }
}