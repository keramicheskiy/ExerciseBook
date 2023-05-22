package com.example.exercisebook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisebook.AddingWorkoutFragment
import com.example.exercisebook.R
import com.example.exercisebook.Utils.fixme
import com.example.exercisebook.databinding.ExerciseItemBinding

class ListOfExercisesAdapter(
    private val context: Context,
    private val list: MutableList<String>
//    private val
    ) : RecyclerView.Adapter<ListOfExercisesAdapter.ViewHolder>() {
    class ViewHolder (val binding : ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(ExerciseItemBinding.bind(view))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]



        holder.binding.nameOfExercise.text = model
        holder.binding.numberOfExercise.text = (list.indexOf(model) + 1).toString()

        holder.binding.removeItem.setOnClickListener {
            list.removeAt(position)
//            AddingWorkoutFragment().removeItemFromList(position)
            fixme.mListOfExercises.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, list.size)
        }

        holder.binding.element.setOnClickListener {

        }


    }





    override fun getItemCount(): Int {
        return list.size
    }

}