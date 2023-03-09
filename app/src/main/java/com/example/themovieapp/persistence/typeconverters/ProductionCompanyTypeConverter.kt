package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.themovieapp.data.vos.ProductionCompanyVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductionCompanyTypeConverter {
    @TypeConverter
    fun toString(productionCompany: List<ProductionCompanyVO>?):String{
        return Gson().toJson(productionCompany)
    }
    @TypeConverter
    fun toProductionCompanies(productionCompanyJsonString: String): List<ProductionCompanyVO>?{
        val productionCompaniesListType = object :TypeToken<List<ProductionCompanyVO>?>() {}.type
        return Gson().fromJson(productionCompanyJsonString,productionCompaniesListType)
    }
}