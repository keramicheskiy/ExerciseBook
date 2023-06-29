package com.example.exercisebook.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val login: String = "",
    val number: String = "",
    val name: String = "",
    val isCouch: Boolean = false,
    
) : Parcelable
