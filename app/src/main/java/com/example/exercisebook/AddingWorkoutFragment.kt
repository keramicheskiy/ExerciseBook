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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.exercisebook.Adapters.ListOfExercisesAdapter
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.DataClasses.Workout
import com.example.exercisebook.Utils.BaseFragment
import com.example.exercisebook.Utils.FireStoreClass
import com.example.exercisebook.Utils.crutch
import com.example.exercisebook.databinding.AddingWorkoutFragmentBinding
import java.util.Calendar


class AddingWorkoutFragment
    : BaseFragment(), DatePickerDialog.OnDateSetListener, ListOfExerciseChange {
    var _binding: AddingWorkoutFragmentBinding? = null
    val binding get() = _binding!!
    private var mProcessDialog: Dialog? = null
    private lateinit var navController: NavController
    var mListOfExercises: MutableList<String> = mutableListOf()
    var mMapOfExercisesWithAttempts: MutableMap<String, MutableList<AttemptDataClass>> = mutableMapOf()
    var mDate: String = ""

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
        mDate = "${day}.${month + 1}.${year}"
        navController = Navigation.findNavController(view)

        binding.date.text = mDate

        binding.btnChangeDate.setOnClickListener {
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.done.setOnClickListener {
            var isNotEmpty = true
            mListOfExercises.forEach { nameOfExercise ->
                val list = mMapOfExercisesWithAttempts[nameOfExercise]
                if (list.isNullOrEmpty()) {
                    isNotEmpty = false
                }
            }
            if (isNotEmpty) {
                binding.uExercises.text = mListOfExercises.toString()
                val workout = Workout(
                    FireStoreClass().getUserEmail()!!,
                    mDate,
                    mListOfExercises,
                    mMapOfExercisesWithAttempts
                )
                FireStoreClass().postWorkoutInCollections(this@AddingWorkoutFragment, workout)
            } else {
                showToast("Заполните все подходы")
            }
        }

        binding.addExercise.setOnClickListener {
            val exercise = binding.enterNameOfExercise.text.toString()
            addExercise(exercise)
        }

        binding.goBack.setOnClickListener {
            navController.navigate(R.id.action_addingWorkoutFragment_to_searchingForWorkoutFragment)
        }


    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        binding.date.text = "$savedDay.$savedMonth.$savedYear"
    }

    fun addExercise(exercise: String) {
        if (exercise.isNotBlank()) {
            if (!mListOfExercises.contains(exercise)) {
                mListOfExercises.add(exercise)

                val adapter = ListOfExercisesAdapter(requireContext(), mListOfExercises, this@AddingWorkoutFragment)
                binding.rvListOfExercises.layoutManager = LinearLayoutManager(activity)
                binding.rvListOfExercises.setHasFixedSize(true)
//                binding.rvListOfExercises.isNestedScrollingEnabled = false
                binding.rvListOfExercises.adapter = adapter
            } else {
                showToast("Данное упражнение уже есть в списке.")
            }
        } else {
            showToast("Введите название упражнения.")
        }
        binding.enterNameOfExercise.text.clear()
    }

    fun workoutPostingSuccess() {
        showToast("Тренировка добавлена")
//        navController.navigate(R.id.)
    }
    fun workoutPostingFailure() {
        showToast("Ошибка! Попробуйте снова.")
    }


}





