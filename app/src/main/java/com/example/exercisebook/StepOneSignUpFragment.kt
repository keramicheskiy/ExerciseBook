package com.example.exercisebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.exercisebook.Utils.FireStoreClass
import com.example.exercisebook.databinding.StepOneSignUpFragmentBinding

class StepOneSignUpFragment : Fragment() {
    private var _binding: StepOneSignUpFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//      TODO(ЗДЕСЬ НЕЛЬЗЯ БЛЯТЬ ПИСАТЬ СВОЮ ХУИТУ ЕБЛАН, ОНО ЗНАЧИТ, ЧТО ТУТ ЕЩЕ НЕТ БАЙНДИНГА !!!!!!!!!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StepOneSignUpFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.goToTheNextStep.setOnClickListener {
            if (checkIfEverithingIsFilledCorrectly()) {
                val login = binding.enterEmail.text.toString().trim{it<=' '}.decapitalize()
                val password = binding.enterPassword.text.toString().trim{it<=' '}
                val bundle = bundleOf(
                    "login" to login,
                    "password" to password
                )

                FireStoreClass().checkIfAccountWithSameLoginAlreadyExists(this@StepOneSignUpFragment, bundle)

            }
        }
    }



    fun checkIfEverithingIsFilledCorrectly() : Boolean {
        val login = binding.enterEmail.text.toString()
        val password1 = binding.enterPassword.text.toString()
        val password2 = binding.enterRepeatPassword.text.toString()
        var result = false

        if (password1 == password2) {
            if (!login.isBlank() && !password1.isBlank() && !password2.isBlank()) {
                result = true
            }
            else {
                Toast.makeText(activity, "Введите все данные", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(activity, "Пароли не совпадают", Toast.LENGTH_LONG).show()
        }

        return result

    }

    fun goToTheNextStep(bundle: Bundle) {
        navController.navigate(
            R.id.action_StepOneSignUpFragment_to_StepTwoSignUpFragment,
            bundle
        )
    }

    fun accountAlreadyExists() {
        binding.enterEmail.text.clear()
        Toast.makeText(activity, "Данная учетная запись уже существует", Toast.LENGTH_LONG).show()
    }


}