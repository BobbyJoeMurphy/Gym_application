package com.example.gym_application.Fragments


import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gym_application.Data.*
import com.example.gym_application.History
import com.example.gym_application.Model.Feed
import com.example.gym_application.Model.NewsAdapter
import com.example.gym_application.R
import com.example.gym_application.Register_Details
import com.example.gym_application.databinding.FragmentProfileBinding
import com.example.gym_application.userData.Login_Activity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Profile_Fragment : Fragment(R.layout.fragment_profile_) {
    private val userViewModel: UserViewModel by viewModels {
        Factory((requireActivity().application as UserApplication).repository)
    }

    private lateinit var binding: FragmentProfileBinding
    lateinit var firebaseAuth: FirebaseAuth
    var currentuser = FirebaseAuth.getInstance().currentUser!!.uid
    private lateinit var datareferance: DatabaseReference
    val uid = FirebaseAuth.getInstance().uid ?: ""
    var history = arrayListOf<HashMap<String, Any>>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()


        val database =
            FirebaseDatabase.getInstance("https://gym-application-3853d-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRef = database.getReference("users/$uid")

        //if items are null go back to register details page




        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val s = snapshot.child("username").value
                if (s == null)
                {
                    val intent = Intent(requireContext(), Register_Details::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    return

                }
                val height = snapshot.child("height").value
                history = snapshot.child("history").value as ArrayList<HashMap<String, Any>>

                val weight = (history as ArrayList<HashMap<String,Long>>).last()["weight"]
                val photo = snapshot.child("profileImageUrl").value
                val photo1 = photo.toString()
                val gender = snapshot.child("gender").value
                val bmi = weight.toString().toInt()
                val bmi2 = height.toString().toInt()
                val value = (bmi * 703) / (bmi2 * bmi2)
                Picasso.get().load(photo1).into(binding.imageView)
                binding.textView3.text = gender.toString()
                binding.textView2.text = value.toString()
                binding.textView4.text = weight.toString()
                binding.textView.text = s.toString()

                if (gender == "Male" && value < 18.5)
                    binding.textView2.setTextColor(Color.parseColor("#5ABBFF"))
                else{
                    if (gender == "Male" && value > 35)
                    binding.textView2.setTextColor(Color.parseColor("#FF0000"))

                else {
                        if (gender == "Male" && value >= 18.5 && value <= 24.9)
                            binding.textView2.setTextColor(Color.parseColor("#00FF0E"))
                        else {
                            if (gender == "Male" && value >= 24.9 && value <= 29.9)
                                binding.textView2.setTextColor(Color.parseColor("#00FF0E"))
                            else {
                                if (gender == "Male" && value >= 29.9 && value <= 34.9)
                                    binding.textView2.setTextColor(Color.parseColor("#FFA72B"))
                            }
                        }
                    }
                }
            }






            override fun onCancelled(error: DatabaseError) {
            }
        })
        binding.cardView3.setOnClickListener{
            var alert = AlertDialog.Builder(context)
            val edittext = EditText(context)
            edittext.inputType = InputType.TYPE_CLASS_NUMBER
            alert.setMessage("Enter Your Message")
            alert.setTitle("Enter Your Title")

            alert.setView(edittext)

            alert.setPositiveButton(
                "Yes Option"
            ) { dialog, whichButton -> //What ever you want to do with the value
                //OR
                val myFormat = "dd-MM-yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.UK)
                val weight = edittext.text.toString().toInt()
                val historyHashMap = hashMapOf<String, Any>(
                    "weight" to weight,
                    "date" to sdf.format(Calendar.getInstance().time)
                )
                val myRef1 = database.getReference("users/$uid/history")
                history.add(historyHashMap)
                myRef1.setValue(history)

            }
            alert.show();

        }

        binding.cardView2.setOnClickListener{

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.Bmi))
                .setMessage(resources.getString(R.string.ContextOfBmi))
                .setNeutralButton(resources.getString(android.R.string.ok)) {_,_ ->
                }
                .show()

        }




        binding.cardView4.setOnClickListener {
            val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.profile_nav)
            bottom.selectedItemId = R.id.WorkOut

        }


        return binding.root
    }


    fun readingDataFromFireBaseToRecycler(){
        val database =
            FirebaseDatabase.getInstance("https://gym-application-3853d-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRef = database.getReference("feed")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val feed = snapshot.value as ArrayList<HashMap<String, String>>
                val newsList =feed.map {
                    Feed(title = it["title"], description = it["description"])
                }
                val adapter =   NewsAdapter(newsList){
                    val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.profile_nav)
                    bottom.tag = it
                    bottom.selectedItemId = R.id.Explore

                }


                binding.recyclers.adapter = adapter








            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        userViewModel.allWords.observe(viewLifecycleOwner) { exercises ->

            val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.profile_nav)
            bottom.tag = 0
            //each type of exercise is linked to the exercise taking the object and finding out what its linked to
            val allType: HashMap<Exercises, Pair<Exercises, ArrayList<TypeOfExercise>>> =
                hashMapOf()
            exercises.forEach { e ->
                if (allType[e] == null) {
                    allType[e] = e to arrayListOf()
                }
                allType[e]?.second?.addAll(e.list)
            }
            val selectedTypes = arrayListOf<Pair<Exercises, TypeOfExercise>>()
            allType.values.forEach { pair ->
                pair.second.removeAll { g -> !g.selected }
                pair.second.forEach { filteredType ->
                    selectedTypes.add(pair.first to filteredType)

                }
            }
            binding.textView5.text = selectedTypes.size.toString()

        }
        readingDataFromFireBaseToRecycler()


    }


}





