package com.example.exercisebook.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workout(
    val login: String = "",
    val date: String = "",
    val listOfNamesOfExercises: MutableList<String> = mutableListOf(),
    val mapOfExercisesWithAttempts: MutableMap<String, MutableList<AttemptDataClass>> = mutableMapOf(),

    ) : Parcelable
