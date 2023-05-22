package com.example.exercisebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.exercisebook.DataBases.DataStoreManager
import com.example.exercisebook.DataClasses.User
import com.example.exercisebook.Utils.Constants
import com.example.exercisebook.Utils.FireStoreClass
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.lang.StringBuilder
import java.util.concurrent.CountDownLatch

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.splash_screen)



        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()


        Handler(Looper.getMainLooper()).postDelayed({
            if (FireStoreClass().getUserID().isNotEmpty()) {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                intent.putExtra(Constants.USER, FireStoreClass().getUserEmail())
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashScreen, LogInActivity::class.java)
                startActivity(intent)
            }

            finish()
        }, 2000)

    }




}