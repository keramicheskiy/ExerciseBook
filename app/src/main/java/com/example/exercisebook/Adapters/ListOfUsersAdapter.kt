package com.example.exercisebook.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.DataClasses.User
import com.example.exercisebook.R
import com.example.exercisebook.SearchingForWorkoutFragment
import com.example.exercisebook.databinding.ExerciseItemBinding
import com.example.exercisebook.databinding.UserItemBinding

class ListOfUsersAdapter(
    val context: Context,
    val list: MutableList<User>,
//    val mapOfExercises: MutableMap<String, AttemptDataClass>,
    val fragment: SearchingForWorkoutFragment,
    ) : RecyclerView.Adapter<ListOfUsersAdapter.ViewHolder>() {
    class ViewHolder( val binding : UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(UserItemBinding.bind(view))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.binding.tvName.text = model.name
        holder.binding.tvNumber.text = model.number

        holder.binding.root.setOnClickListener {
            fragment.goToUsersWorkout(model.login)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}