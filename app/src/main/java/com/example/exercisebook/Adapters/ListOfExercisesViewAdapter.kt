package com.example.exercisebook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.R
import com.example.exercisebook.WorkoutFragment
import com.example.exercisebook.databinding.AttemptItemBinding
import com.example.exercisebook.databinding.ExerciseItemBinding
import com.example.exercisebook.databinding.ExerciseViewItemBinding

class ListOfExercisesViewAdapter(
    val context: Context,
    val listOfExercises: MutableList<String>,
//    val mapOfExercisesWithAttempts: MutableMap<String, MutableList<AttemptDataClass>>,
    val fragment: WorkoutFragment,
    ) : RecyclerView.Adapter<ListOfExercisesViewAdapter.ViewHolder>() {
    class ViewHolder(val binding: ExerciseViewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.exercise_view_item, parent, false)
        return ViewHolder(ExerciseViewItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nameOfExercise = listOfExercises[position] // Название упражнения

        holder.binding.nameOfExercise.text = nameOfExercise
        holder.binding.numberOfExercise.text = (listOfExercises.indexOf(nameOfExercise) + 1).toString()

        holder.binding.root.setOnClickListener {
            val adapter = ListOfAttemptsViewAdapter(
                context,
                fragment.mMapOfExercisesWithAttempts[nameOfExercise]!!,
                nameOfExercise,
                fragment,
            )
            holder.binding.rvListOfAttempts.layoutManager = LinearLayoutManager(context)
            holder.binding.rvListOfAttempts.setHasFixedSize(false)
            holder.binding.rvListOfAttempts.adapter = adapter
        }



    }

    override fun getItemCount(): Int {
        return listOfExercises.size
    }










}