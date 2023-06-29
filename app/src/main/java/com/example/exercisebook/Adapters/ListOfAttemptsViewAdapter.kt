package com.example.exercisebook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisebook.AddingWorkoutFragment
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.R
import com.example.exercisebook.WorkoutFragment
import com.example.exercisebook.databinding.AttemptItemBinding

class ListOfAttemptsViewAdapter(
    private val context: Context,
    private val list: MutableList<AttemptDataClass>,
    private val nameOfExercise: String,
    private val fragment: WorkoutFragment
) : RecyclerView.Adapter<ListOfAttemptsViewAdapter.ViewHolder>() {
    class ViewHolder (val binding : AttemptItemBinding) : RecyclerView.ViewHolder(binding.root)

    var listOfAttempts = mutableListOf<AttemptDataClass>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.attempt_item, parent, false)
        return ViewHolder(AttemptItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.binding.tvWeight.text = model.weight.toString()
        holder.binding.tvTries.text = model.tries.toString()
        when(model.tries.toInt()) {
            1 ->  holder.binding.textTries.text = "повторение"
            2 ->  holder.binding.textTries.text = "повторения"
            3 ->  holder.binding.textTries.text = "повторения"
            4 ->  holder.binding.textTries.text = "повторения"
            else -> holder.binding.textTries.text = "повторений"
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}