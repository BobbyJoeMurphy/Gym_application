package com.example.gym_application

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gym_application.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class Register_Activity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("logging in")
        progressDialog.setCanceledOnTouchOutside(false)

        //Instlising firebase Auth loggin
        firebaseAuth = FirebaseAuth.getInstance()




        // handle click for button
        binding.BtnRegister.setOnClickListener {

            //valadating data
            validateData()
            //fire store saving the registerd users name

        }
    }


    private fun validateData() {
        email = binding.Email.text.toString().trim()
        password = binding.password2.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.Email.error = "invalid email"
        } else if (TextUtils.isEmpty(password)) {
            binding.password2.error = "invalid password"
        } else if (password.length < 6) {
            binding.password2.error = "invalid password must be atleast 6 chars long"
        } else
            firebaseSignup()

    }



    private fun firebaseSignup() {
        progressDialog.show()
        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Register_Details::class.java)
                //open  profile
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "SignUp failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }






    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}





