package com.example.exercisebook

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.exercisebook.Utils.FireStoreClass
import com.example.exercisebook.databinding.MainActivityBinding


class MainActivity : AppCompatActivity(){
    lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.signOut.setOnClickListener {
//            FireStoreClass().Auth.signOut()
//        }


    }
}