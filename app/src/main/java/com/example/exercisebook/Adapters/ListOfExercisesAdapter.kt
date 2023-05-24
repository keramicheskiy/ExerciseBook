package com.example.exercisebook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisebook.AddingWorkoutFragment
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.R
import com.example.exercisebook.ListOfExerciseChange
import com.example.exercisebook.Utils.crutch
import com.example.exercisebook.databinding.ExerciseItemBinding

class ListOfExercisesAdapter (
    private val context: Context,
    private val list: MutableList<String>,
    private val fragment: AddingWorkoutFragment,

    ) : RecyclerView.Adapter<ListOfExercisesAdapter.ViewHolder>() {
    class ViewHolder (val binding : ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root)
    var listOfAttempts = mutableListOf<AttemptDataClass>()
    lateinit var adapter: ListOfAttemptsAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(ExerciseItemBinding.bind(view))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nameOfExercise = list[position] // Название упражнения

        holder.binding.nameOfExercise.text = nameOfExercise
        holder.binding.numberOfExercise.text = (list.indexOf(nameOfExercise) + 1).toString()

        onStartShowListOfAttemptsAdapter(holder, nameOfExercise)

        holder.binding.removeItem.setOnClickListener {
            removeExercise(nameOfExercise, position)
        }

        holder.binding.addAttempt.setOnClickListener {
            val weight = holder.binding.enterWeight.text.toString().trim{it<=' '}
            val tries = holder.binding.enterTries.text.toString().trim{it<=' '}

            if (isFilledCorrectly(weight, tries)) {
                addAttempt(holder, nameOfExercise)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



    private fun isFilledCorrectly(weight: String, tries: String): Boolean {
        return !weight.isBlank() && !tries.isBlank()
    }

    private fun addAttempt(holder: ViewHolder, nameOfExercise: String) {
        var listOfAttempts = mutableListOf<AttemptDataClass>()

        val attempt = AttemptDataClass(
            holder.binding.enterWeight.text.toString().toInt(),
            holder.binding.enterTries.text.toString().toInt(),
        )

        if (fragment.mMapOfExercisesWithAttempts[nameOfExercise] != null) {
            listOfAttempts = fragment.mMapOfExercisesWithAttempts[nameOfExercise]!!
            listOfAttempts.add(attempt)
        }
        fragment.mMapOfExercisesWithAttempts.put(nameOfExercise, listOfAttempts)

        val adapter = ListOfAttemptsAdapter(context, listOfAttempts, nameOfExercise, fragment)
        holder.binding.rvListOfAttempts.layoutManager = GridLayoutManager(context, 1)
        holder.binding.rvListOfAttempts.setHasFixedSize(false)
        holder.binding.rvListOfAttempts.adapter = adapter
    }

    private fun removeExercise(nameOfExercise: String, position: Int) {
        fragment.mListOfExercises.remove(nameOfExercise)
        fragment.mMapOfExercisesWithAttempts.remove(nameOfExercise)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

    private fun onStartShowListOfAttemptsAdapter(holder: ViewHolder, nameOfExercise: String) {
        if (fragment.mMapOfExercisesWithAttempts[nameOfExercise] != null) {
            adapter = ListOfAttemptsAdapter(context, fragment.mMapOfExercisesWithAttempts[nameOfExercise]!!, nameOfExercise, fragment)
        } else {
            adapter = ListOfAttemptsAdapter(context, listOfAttempts, nameOfExercise, fragment)
        }
        holder.binding.rvListOfAttempts.layoutManager = GridLayoutManager(context, 1)
        holder.binding.rvListOfAttempts.setHasFixedSize(false)
        holder.binding.rvListOfAttempts.adapter = adapter
    }

}