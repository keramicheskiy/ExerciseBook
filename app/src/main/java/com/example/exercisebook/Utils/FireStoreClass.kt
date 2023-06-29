package com.example.exercisebook.Utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.exercisebook.*
import com.example.exercisebook.DataClasses.AttemptDataClass
import com.example.exercisebook.DataClasses.User
import com.example.exercisebook.DataClasses.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.io.IOException
import java.util.concurrent.CountDownLatch

class FireStoreClass {
    val mFireStore = FirebaseFirestore.getInstance()
    val Auth = FirebaseAuth.getInstance()

    fun createUserInCollections(fragment: Fragment, user: UserBuilder) {
        val userInstance = user.build()
        mFireStore.collection(Constants.USERS)
            .document()
            .set(userInstance, SetOptions.merge())
            .addOnSuccessListener {
                when(fragment) {
                    is StepTwoSignUpFragment -> fragment.registrationSuccess(userInstance)
                }
            }.addOnFailureListener {
                when(fragment) {
                    is StepTwoSignUpFragment -> fragment.registrationFailure()
                }
            }
    }

    fun registerUserInAuth(fragment: Fragment, user: UserBuilder, password: String) {

        Auth.createUserWithEmailAndPassword(user.login!!, password!!)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                val firebaseUser = it.result!!.user!!
                val userId = firebaseUser.uid
                user.setId(userId)

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

    fun registerUser(fragment: Fragment, user: UserBuilder, password: String) {
        val userInstance = user.build()
        mFireStore.collection(Constants.USERS)
            .whereEqualTo("number", userInstance.number)
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

    fun postWorkoutInCollections(fragment: Fragment, workout: Workout) {
        mFireStore.collection(Constants.WORKOUT)
            .document()
            .set(workout, SetOptions.merge())
            .addOnSuccessListener {
                when(fragment) {
                    is AddingWorkoutFragment -> fragment.workoutPostingSuccess()
                }
            }.addOnFailureListener {

            }
    }

    fun findAndShowAllWorkouts(fragment: Fragment, name: String, date: String) {
        mFireStore.collection(Constants.USERS)
            .whereEqualTo(Constants.NAME, name)
            .get()
            .addOnSuccessListener {
                var list = mutableListOf<User>()

                for (i in it.documents) {
                    val snapshot = i.toObject(User::class.java)
                    list.add(snapshot!!)
                }

                when(fragment) {
                    is SearchingForWorkoutFragment -> fragment.createListOfUsersAdapter(list)
                }

            }
    }

    fun fillTheAdapterWithExercises(fragment: Fragment, email: String, date: String) {
        mFireStore.collection(Constants.WORKOUT)
            .whereEqualTo(Constants.LOGIN, email)
            .whereEqualTo(Constants.DATE, date)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    when(fragment) {
                        is WorkoutFragment -> fragment.createAttemptViewAdapter(it.toObjects(Workout::class.java))
                    }
                } else {
                    when(fragment) {
                        is WorkoutFragment -> fragment.destroy()
                    }
                }
            }.addOnFailureListener {
                when(fragment) {
                    is WorkoutFragment -> fragment.destroy()
                }
            }
    }


}