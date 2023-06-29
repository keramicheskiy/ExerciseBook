package com.example.exercisebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisebook.Adapters.ListOfAttemptsAdapter
import com.example.exercisebook.Adapters.ListOfExercisesViewAdapter
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.DataClasses.Workout
import com.example.exercisebook.Utils.FireStoreClass
import com.example.exercisebook.databinding.WorkoutFragmentBinding
import java.util.*

class WorkoutFragment : Fragment() {
    var _binding: WorkoutFragmentBinding? = null
    val binding get() = _binding!!

    lateinit var date: String
    lateinit var email: String
    var mListOfExercises: MutableList<String> = mutableListOf()
    var mMapOfExercisesWithAttempts: MutableMap<String, MutableList<AttemptDataClass>> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        date = requireArguments().getString("date")!!
        email = requireArguments().getString("email")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WorkoutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        binding.date.text = date

        FireStoreClass().fillTheAdapterWithExercises(this, email, date)

        binding.goBack.setOnClickListener {
            navController.navigate(R.id.action_workoutFragment_to_searchingForWorkoutFragment)
        }


    }

    fun destroy() {
        Toast.makeText(requireContext(), "Ошибка!", Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    fun createAttemptViewAdapter(list: MutableList<Workout>) {
        if (list.size == 1) {
            var workout = list[0]
            mMapOfExercisesWithAttempts = workout.mapOfExercisesWithAttempts
            val adapter = ListOfExercisesViewAdapter(
                requireContext(),//TODO context
                workout.listOfNamesOfExercises,
//                workout.mapOfExercisesWithAttempts,
                this,
            )
            binding.rvListOfExercises.layoutManager = LinearLayoutManager(activity)
            binding.rvListOfExercises.setHasFixedSize(false)
            binding.rvListOfExercises.adapter = adapter

        } else {
            Toast.makeText(requireContext(), "жесть", Toast.LENGTH_LONG).show()
        }
    }


}