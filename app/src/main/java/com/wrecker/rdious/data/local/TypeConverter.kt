package com.wrecker.rdious.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wrecker.rdious.domain.entities.FacilityOption

class TypeConverter {

    @TypeConverter
    fun fromSource(source: List<FacilityOption>): String{
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSource(string: String): List<FacilityOption>{
        val typeToke = object : TypeToken<List<FacilityOption>>(){}.type
        return Gson().fromJson(string, typeToke)
    }
}