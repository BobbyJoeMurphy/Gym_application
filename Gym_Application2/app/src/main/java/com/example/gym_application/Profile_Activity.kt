package com.example.gym_application.userData


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gym_application.Data.Factory
import com.example.gym_application.Data.UserApplication
import com.example.gym_application.Data.UserDao
import com.example.gym_application.Data.UserViewModel
import com.example.gym_application.MainActivity
import com.example.gym_application.R

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class Profile_Activity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userDao: UserDao



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseAuth = FirebaseAuth.getInstance()






        val navController = findNavController(R.id.fragmentContainerView2)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.profile_nav)
        bottomNavigationView.setupWithNavController(navController)
    }
    override fun onBackPressed() {

        findNavController(R.id.fragmentContainerView2).popBackStack()


    }

    fun logout(view: View) {
        firebaseAuth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }


}