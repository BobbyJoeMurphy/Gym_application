package com.example.gym_application.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gym_application.R
import com.example.gym_application.databinding.FragmentProgressBinding
import com.example.gym_application.databinding.FragmentTricepsBinding
import com.github.mikephil.charting.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Progress_Fragment : Fragment() {
    private lateinit var binding: FragmentProgressBinding
    private lateinit var datareferance: DatabaseReference
    val uid = FirebaseAuth.getInstance().uid ?: ""
    var history = arrayListOf<HashMap<String, Any>>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(inflater, container, false)

        val database =
            FirebaseDatabase.getInstance("https://gym-application-3853d-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRef = database.getReference("users/$uid")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                history = snapshot.child("history").value as ArrayList<HashMap<String, Any>>
                val entries = history.mapIndexed { index, s ->
                    val weight = s["weight"] as Long
                    val date = s["date"]
                    BarEntry(index.toFloat(), weight?.toFloat() ?: 0f)

                }
                val dataSet = LineDataSet(entries, "Weight") // add entries to dataset
                val lineChart = LineData(dataSet)
                binding.chart.data = lineChart
                binding.chart.invalidate(); // refresh

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })







        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


}