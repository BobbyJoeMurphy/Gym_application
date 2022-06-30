package com.example.gym_application

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.gym_application.databinding.ActivityRegisterDetailsBinding
import com.example.gym_application.userData.Profile_Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class Register_Details : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterDetailsBinding
    private lateinit var btnDatePicker: Button
    private lateinit var firebaseAuth: FirebaseAuth
    var currentuser = FirebaseAuth.getInstance().currentUser!!.uid
    private var Dob = ""
    lateinit var male: RadioButton
    lateinit var female: RadioButton
    lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        male = findViewById(R.id.male)
        female = findViewById(R.id.female)

        male.setOnClickListener {
            gender = "Male"
        }
        female.setOnClickListener {
            gender = "Female"
        }

        //datepicker
        btnDatePicker = findViewById(R.id.btnDatePicker)
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateable(myCalendar)
        }

        //handle click for date
        btnDatePicker.setOnClickListener {
            DatePickerDialog(
                this, datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.imageView4.setOnClickListener {
            Log.d("fragment_Upload_button", "try to show photo")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ("image/*")
            startActivityForResult(intent, 0)
        }
//         handle click for button
        binding.btnRegister.setOnClickListener {


            uploadImageToFirebaseStorage()


        }


    }
    private fun updateable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        Dob = (sdf.format(myCalendar.time))
    }

    var seletedPhotoUri: Uri? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)
            Log.d("register", "new photo selected")
        seletedPhotoUri = data?.data
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, seletedPhotoUri)
        val bitmapDrawable = BitmapDrawable(bitmap)
        binding.imageView4.setBackgroundDrawable(bitmapDrawable)
    }


    private fun uploadImageToFirebaseStorage() {
        if (seletedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().reference.child("/images/$filename")
        ref.putFile(seletedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("register", "success")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("register_Activity", "filelocation $it")
                    saveToFireBaseRealTime(it.toString())

                }
            }
    }

    private fun saveToFireBaseRealTime(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref =
            FirebaseDatabase.getInstance("https://gym-application-3853d-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("/users/$uid")

        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)

        val history = History(
            binding.Weight.text.toString().toInt(),
            sdf.format(Calendar.getInstance().time)


            )
        val user1 = User1(
            uid,
            binding.name.text.toString(),
            listOf(history),
            binding.height.text.toString().toInt(),
            Dob,
            gender,
            profileImageUrl
        )
        ref.setValue(user1)
        val intent = Intent(this, Profile_Activity::class.java)
        //open  profile
        startActivity(intent)
        finish()
    }

}

class User1(
    val uid: String,
    val Username: String,
    val history : List<History>,
    val Height: Int,
    val Dob: String,
    val gender: String,
    val profileImageUrl: String
)
class History(
    val Weight: Int,
    val Date: String
)

