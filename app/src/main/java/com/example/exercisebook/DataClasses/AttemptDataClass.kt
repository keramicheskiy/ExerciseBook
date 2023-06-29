package com.example.exercisebook.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttemptDataClass(
    val weight: Float = 0f,
    val tries: Int = 0

) : Parcelable
