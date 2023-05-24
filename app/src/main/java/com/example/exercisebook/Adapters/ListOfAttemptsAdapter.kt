package com.example.exercisebook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisebook.AddingWorkoutFragment
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.R
import com.example.exercisebook.Utils.crutch
import com.example.exercisebook.databinding.AttemptItemBinding
import com.example.exercisebook.databinding.ExerciseItemBinding

class ListOfAttemptsAdapter (
    private val context: Context,
    private val list: MutableList<AttemptDataClass>,
    private val nameOfExercise: String,
    private val fragment: AddingWorkoutFragment
        ) : RecyclerView.Adapter<ListOfAttemptsAdapter.ViewHolder>(), View.OnClickListener {
    class ViewHolder (val binding : AttemptItemBinding) : RecyclerView.ViewHolder(binding.root)

    var listOfAttempts = mutableListOf<AttemptDataClass>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.attempt_item, parent, false)
        return ListOfAttemptsAdapter.ViewHolder(AttemptItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.binding.tvWeight.text = model.weight.toString()
        holder.binding.tvTries.text = model.tries.toString()

        holder.binding.removeItem.setOnClickListener {
            removeAttempt(model, position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun removeAttempt(model: AttemptDataClass, position: Int) {

        if (fragment.mMapOfExercisesWithAttempts[nameOfExercise] != null) {
            listOfAttempts = fragment.mMapOfExercisesWithAttempts[nameOfExercise]!!
            listOfAttempts.remove(model)
        }
        fragment.mMapOfExercisesWithAttempts.put(nameOfExercise, listOfAttempts)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}

