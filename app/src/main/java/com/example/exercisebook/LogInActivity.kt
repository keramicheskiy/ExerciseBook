package com.example.exercisebook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.exercisebook.LogInFragment
import com.example.exercisebook.databinding.LogInActivityBinding


class LogInActivity : AppCompatActivity(){// , View.OnClickListener
    lateinit var binding: LogInActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LogInActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

//    fun openFragment(context: Context, Fragment) {
//        val fragment1: LogInFragment = MainFragment()
//        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
//        ft.replace(R.id., f)
//        Log.e("------", f.arguments.toString())
//        ft.commit()
//    }

//    override fun onClick(v: View?) {
//        when(v!!.id) {
//            R.id.btnRegister -> startActivity(Intent(this, ::class.java))
//        }
//
//    }

}