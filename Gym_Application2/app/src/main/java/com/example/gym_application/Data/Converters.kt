package com.example.gym_application.Data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<TypeOfExercise> {
        val listType: Type = object : TypeToken<ArrayList<TypeOfExercise>>() {}.type
        return Gson().fromJson(value, listType)
    }



    @TypeConverter
    fun fromArrayList(list: ArrayList<TypeOfExercise>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}