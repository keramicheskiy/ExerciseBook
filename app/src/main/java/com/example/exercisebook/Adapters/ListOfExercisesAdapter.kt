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
        val model = list[position] // Название упражнения

        holder.binding.nameOfExercise.text = model
        holder.binding.numberOfExercise.text = (list.indexOf(model) + 1).toString()

        onStartShowListOfAttemptsAdapter(holder, model)

        holder.binding.removeItem.setOnClickListener {
            removeExercise(model, position)
        }

        holder.binding.addAttempt.setOnClickListener {
            addAttempt(holder, model)
        }


    }





    override fun getItemCount(): Int {
        return list.size
    }

    fun addAttempt(holder: ViewHolder, model: String) {

        val attempt = AttemptDataClass(
            holder.binding.enterWeight.text.toString().toInt(),
            holder.binding.enterTries.text.toString().toInt(),
        )

        if (fragment.mMapOfExercisesWithAttempts[model] != null) {
            listOfAttempts = fragment.mMapOfExercisesWithAttempts[model]!!
            listOfAttempts.add(attempt)
        }
        fragment.mMapOfExercisesWithAttempts.put(model, listOfAttempts)

        val adapter = ListOfAttemptsAdapter(context, listOfAttempts, model, fragment)
        holder.binding.rvListOfAttempts.layoutManager = GridLayoutManager(context, 1)
        holder.binding.rvListOfAttempts.setHasFixedSize(false)
        holder.binding.rvListOfAttempts.adapter = adapter
    }

    fun removeExercise(model: String, position: Int) {
        fragment.mListOfExercises.remove(model)
        fragment.mMapOfExercisesWithAttempts.remove(model)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

    fun onStartShowListOfAttemptsAdapter(holder: ViewHolder, model: String) {
        if (fragment.mMapOfExercisesWithAttempts[model] != null) {
            adapter = ListOfAttemptsAdapter(context, fragment.mMapOfExercisesWithAttempts[model]!!, model, fragment)
        } else {
            adapter = ListOfAttemptsAdapter(context, listOfAttempts, model, fragment)
        }
        holder.binding.rvListOfAttempts.layoutManager = GridLayoutManager(context, 1)
        holder.binding.rvListOfAttempts.setHasFixedSize(false)
        holder.binding.rvListOfAttempts.adapter = adapter
    }

}