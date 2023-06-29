package com.example.exercisebook

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercisebook.Adapters.ListOfUsersAdapter
import com.example.exercisebook.DataClasses.User
import com.example.exercisebook.Utils.FireStoreClass
import com.example.exercisebook.databinding.SearchingForWorkoutFragmentBinding
import java.util.Calendar


class SearchingForWorkoutFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    var _binding: SearchingForWorkoutFragmentBinding? = null
    val binding get() = _binding!!

    var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var month = Calendar.getInstance().get(Calendar.MONTH)
    var year = Calendar.getInstance().get(Calendar.YEAR)
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var date: String = "$day.${month+1}.$year"
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchingForWorkoutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.btnChooseDate.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.btnSearch.setOnClickListener {
//            Toast.makeText(requireContext(), date, Toast.LENGTH_LONG).show()
            val firstName = binding.enterFirstName.text.toString().trim{it<=' '}
            val lastName = binding.enterLastName.text.toString().trim{it<=' '}
            val name = "$firstName $lastName"

            FireStoreClass().findAndShowAllWorkouts(
                this@SearchingForWorkoutFragment, name, date)
        }

        binding.llAddingWorkout.setOnClickListener {
            navController.navigate(R.id.action_searchingForWorkoutFragment_to_addingWorkoutFragment)
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year
        date = "$savedDay.$savedMonth.$savedYear"
        Toast.makeText(requireContext(), date, Toast.LENGTH_LONG).show()
    }

    fun createListOfUsersAdapter(list: MutableList<User>) {
        val adapter = ListOfUsersAdapter(requireContext(), list, this@SearchingForWorkoutFragment)
        binding.rvListOfWorkouts.layoutManager = LinearLayoutManager(activity)
        binding.rvListOfWorkouts.setHasFixedSize(true)
        binding.rvListOfWorkouts.adapter = adapter
    }

    fun goToUsersWorkout(email: String) {
        val bundle = bundleOf("date" to date, "email" to email)
        navController.navigate(R.id.action_searchingForWorkoutFragment_to_workoutFragment, bundle)
    }



}