package com.example.exercisebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.exercisebook.R
import com.example.exercisebook.databinding.LogInFragmentBinding

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent
import androidx.core.os.bundleOf
import com.example.exercisebook.Utils.FireStoreClass


class LogInFragment: Fragment(){
    var _binding: LogInFragmentBinding? = null
    val binding get() = _binding!!
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LogInFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.btnRegister.setOnClickListener {
            navController.navigate( R.id.action_LogInFragment_to_StepOneSignUpFragment )
        }

        binding.signInButton.setOnClickListener {
            val login = binding.enterEmail.text.toString().trim{ it<=' ' }.decapitalize()
            val password = binding.enterPassword.text.toString().trim{ it<=' ' }

            if (isAreFieldsFilledCorrectly(login, password)) {

                FireStoreClass().Auth.signInWithEmailAndPassword(login, password)
                    .addOnSuccessListener {
                        Toast.makeText(activity, "Вход выполнен", Toast.LENGTH_LONG).show()
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }.addOnFailureListener {
                        Toast.makeText(activity, "Логин или пароль неверен", Toast.LENGTH_LONG).show()
                    }

            }
        }


    }


    fun isAreFieldsFilledCorrectly(login: String, password: String) : Boolean {

        return (login.isNotBlank() && password.isNotBlank())
    }






}