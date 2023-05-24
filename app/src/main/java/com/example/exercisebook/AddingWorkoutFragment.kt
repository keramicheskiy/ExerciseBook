package com.example.exercisebook

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.exercisebook.Adapters.ListOfExercisesAdapter
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.Utils.BaseFragment
import com.example.exercisebook.Utils.crutch
import com.example.exercisebook.databinding.AddingWorkoutFragmentBinding
import java.util.Calendar


class AddingWorkoutFragment
    : BaseFragment(), DatePickerDialog.OnDateSetListener, ListOfExerciseChange {
    var _binding: AddingWorkoutFragmentBinding? = null
    val binding get() = _binding!!
    private var mProcessDialog: Dialog? = null
    var mListOfExercises: MutableList<String> = mutableListOf()
    var mMapOfExercisesWithAttempts: MutableMap<String, MutableList<AttemptDataClass>> = mutableMapOf()

    var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var month = Calendar.getInstance().get(Calendar.MONTH)
    var year = Calendar.getInstance().get(Calendar.YEAR)
    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddingWorkoutFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.date.text = "${day}.${month + 1}.${year}"

        binding.btnChangeDate.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.done.setOnClickListener {
            binding.date.text = mListOfExercises.toString()
        }

        binding.addExercise.setOnClickListener {
            val exercise = binding.enterNameOfExercise.text.toString()
            addExercise(exercise)
        }


    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        binding.date.text = "$savedDay.$savedMonth.$savedYear"
    }

//    override fun exerciseDeleted(exercise: String) {
//        mListOfExercises.remove(exercise)
//        showToast(mListOfExercises.toString())
//    }

    fun addExercise(exercise: String) {
        if (exercise.isNotBlank()) {
            if (!mListOfExercises.contains(exercise)) {
                mListOfExercises.add(exercise)

                val adapter = ListOfExercisesAdapter(requireContext(), mListOfExercises, this@AddingWorkoutFragment)
                binding.rvListOfExercises.layoutManager = LinearLayoutManager(activity)
                binding.rvListOfExercises.setHasFixedSize(true)
                binding.rvListOfExercises.adapter = adapter
            } else {
                showToast("Данное упражнение уже есть в списке.")
            }
        } else {
            showToast("Введите название упражнения.")
        }
        binding.enterNameOfExercise.text.clear()
    }

}
