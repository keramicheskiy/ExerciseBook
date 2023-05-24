package com.example.exercisebook.DataClasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttemptDataClass(
    val weight: Int = 0,
    val tries: Int = 0

) : Parcelable
