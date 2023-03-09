package com.example.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.themovieapp.data.vos.BelongsToCollectionVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CollectionTypeConverter {

    @TypeConverter
    fun toString(collection: BelongsToCollectionVO?): String{
        return Gson().toJson(collection)
    }
    @TypeConverter
    fun toCollectionVO(commentListJsonStr: String): BelongsToCollectionVO?{
        val collectionVOType = object : TypeToken<BelongsToCollectionVO?>(){}.type
        return Gson().fromJson(commentListJsonStr,collectionVOType)
    }
}