package com.example.exercisebook

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.exercisebook.DataClasses.User
import com.example.exercisebook.Utils.Constants
import com.example.exercisebook.Utils.FireStoreClass
import com.example.exercisebook.databinding.StepTwoSignUpFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class StepTwoSignUpFragment : Fragment() {
    var _binding: StepTwoSignUpFragmentBinding? = null
    val binding get() = _binding!!
    lateinit var navController: NavController
    var login: String? = null
    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login = requireArguments().getString("login")
        password = requireArguments().getString("password")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StepTwoSignUpFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.signUpButton.setOnClickListener {
            signUp()
        }


    }





    fun checkIfEverithingIsFilledCorrectly() : Boolean {
        val firstName = binding.enterFirstName.text.toString()
        val lastName = binding.enterLastName.text.toString()
        val phoneNumber = binding.enterPhoneNumber.text.toString()

        return !phoneNumber.isBlank() && !firstName.isBlank() && !lastName.isBlank()

    }


    fun signUp(){
        if (checkIfEverithingIsFilledCorrectly()) {
            val number = binding.enterPhoneNumber.text.toString().trim{it<=' '}
            val firstName = binding.enterFirstName.text.toString().trim{it<=' '}
            val lastName = binding.enterLastName.text.toString().trim{it<=' '}
            val isCouch = binding.isCouchCheckBox.isChecked

            val user = User(
                "",
                login!!,
                number,
                firstName,
                lastName,
                isCouch,
            )

            FireStoreClass().registerUser(this@StepTwoSignUpFragment, user, password!!)

        } else {
            Toast.makeText(activity, "Введите все данные", Toast.LENGTH_LONG).show()
        }
    }

    fun registrationSuccess(user: User) {
        Toast.makeText(activity, "Регистрация выполнена", Toast.LENGTH_SHORT)
            .show()
        val intend = Intent(activity, MainActivity::class.java)
        intend.putExtra(Constants.EXTRA_USER_DETAILS, user)
        startActivity(intend)
    }

    fun registrationFailure() {
        Toast.makeText(activity, "Ошибка!", Toast.LENGTH_SHORT)
            .show()
    }


    fun accountAlreadyExists() {
        binding.enterPhoneNumber.text.clear()
        Toast.makeText(activity, "Аккаунт с таким номером уже существует", Toast.LENGTH_LONG).show()
    }


}