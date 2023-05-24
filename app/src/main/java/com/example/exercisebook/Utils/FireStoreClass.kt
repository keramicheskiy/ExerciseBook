package com.example.exercisebook.Utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.exercisebook.DataClasses.User
import com.example.exercisebook.StepOneSignUpFragment
import com.example.exercisebook.StepTwoSignUpFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.io.IOException
import java.util.concurrent.CountDownLatch

class FireStoreClass {
    val mFireStore = FirebaseFirestore.getInstance()
    val Auth = FirebaseAuth.getInstance()

    fun createUserInCollections(fragment: Fragment, user: User) {
        mFireStore.collection(Constants.USERS)
            .document()
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                when(fragment) {
                    is StepTwoSignUpFragment -> fragment.registrationSuccess(user)
                }
            }.addOnFailureListener {
                when(fragment) {
                    is StepTwoSignUpFragment -> fragment.registrationFailure()
                }
            }
    }

    fun registerUserInAuth(fragment: Fragment, user: User, password: String) {

        Auth.createUserWithEmailAndPassword(user.login!!, password!!)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                val firebaseUser = it.result!!.user!!
                val userId = firebaseUser.uid

                val user = User(
                    userId,
                    user.login!!,
                    user.number,
                    user.firstName,
                    user.lastName,
                    user.isCouch,
                )
                createUserInCollections(fragment, user)
            }
        }
    }

    fun checkIfAccountWithSameLoginAlreadyExists(fragment: Fragment, bundle: Bundle) {
        mFireStore.collection(Constants.USERS)
            .whereEqualTo("login", bundle.getString("login"))
            .get()
            .addOnSuccessListener {
                when (fragment) {
                    is StepOneSignUpFragment -> {
                        if(!it.isEmpty) {
                            fragment.accountAlreadyExists()
                        } else {
                            fragment.goToTheNextStep(bundle)
                        }
                    }
                }
            }
    }

    fun registerUser(fragment: Fragment, user: User, password: String) {
        mFireStore.collection(Constants.USERS)
            .whereEqualTo("number", user.number)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    when(fragment) {
                        is StepTwoSignUpFragment -> {
                            registerUserInAuth(fragment, user, password)
                        }
                    }
                } else {
                    when(fragment) {
                        is StepTwoSignUpFragment -> fragment.accountAlreadyExists()
                    }
                }
            }
    }

    fun getUserEmail() : String? {
        val currentUser = Auth.currentUser
        var currentUserEmail = ""
        if (currentUser != null) {
            currentUserEmail = currentUser.email!!
        }
        return currentUserEmail
    }

    fun getUserID() : String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

//    fun signInWithEmailAndPassword(login: String, password: String) {
//        Auth.signInWithEmailAndPassword()
//
//    }

    fun addNewWorkoutToCollections() {

    }


}