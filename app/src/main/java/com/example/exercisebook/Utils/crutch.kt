package com.example.exercisebook.Utils

import com.example.exercisebook.DataClasses.AttemptDataClass

class crutch {

    companion object {
        var mListOfExercises = mutableListOf<String>()
//        var mListOfAttempts = mutableListOf<AttemptDataClass>()
        var mMapOfAttempts = mutableMapOf<String, MutableList<AttemptDataClass>>()
//        var mMapOfAttempts = mutableMapOf<String, AttemptDataClass>()
    }

}