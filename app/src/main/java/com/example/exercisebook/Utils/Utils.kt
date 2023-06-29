package com.example.exercisebook.Utils

import java.io.IOException

class Utils {

    fun checkIsFloat(value : String) : Boolean{
        var res : Boolean = true
        try {
            value.toFloat()
        } catch (e : IOException) {
            res = false
        }
        return res
    }

    fun checkIsInt(value : String) : Boolean{
        var res : Boolean = true
        try {
            value.toInt()
        } catch (e : IOException) {
            res = false
        }
        return res
    }

}