package com.example.gym_application.userData

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.gym_application.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login_Activity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding



    //progression dialog
    private lateinit var progressDialog: ProgressDialog

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("logging in")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.button2.setOnClickListener {
            validateData()

        }
    }
    // checking if input is valid
    private fun validateData() {
        email = binding.username2.text.toString().trim()
        password = binding.password2.text.toString().trim()


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email
            binding.username2.error = "invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            //no password entered
            binding.password2.error = "Please enter a valid password"
        } else {
            //data is valid
            firebaseLogin()
        }
    }
        //logging in
    private fun firebaseLogin() {
        //show progress

        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Login as $email", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Profile_Activity::class.java)
                //open  profile
                startActivity(intent)
                finish()

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            //user is logged in
            val intent = Intent(this, Profile_Activity::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}