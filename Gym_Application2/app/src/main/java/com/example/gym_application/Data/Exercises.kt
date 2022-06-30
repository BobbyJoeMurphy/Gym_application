package com.example.gym_application.Data

import androidx.room.*
import java.io.Serializable


@Entity(tableName = "Exercises_Table")

data class Exercises (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var image: Int,
    @TypeConverters(Converters::class)
    var list: ArrayList<TypeOfExercise>
):Serializable


data class TypeOfExercise (
    val name: String,
    val description: String,
    var reps:Int,
    var weight:Int,
    var selected: Boolean = false
):Serializable






