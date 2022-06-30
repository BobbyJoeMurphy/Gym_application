package com.example.gym_application.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gym_application.Model.Feed
import com.example.gym_application.Model.NewsAdapter
import com.example.gym_application.R
import com.example.gym_application.databinding.FragmentExploreBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Explore_Fragment : Fragment() {
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater, container, false)






        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database =
            FirebaseDatabase.getInstance("https://gym-application-3853d-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRef = database.getReference("feed")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bottom = requireActivity().findViewById<BottomNavigationView>(R.id.profile_nav)
                var index = bottom?.tag.toString().toInt()
                val feed = snapshot.value as ArrayList<HashMap<String, String>>
                val newsItem = feed.map {
                    Feed(title = it["title"], description = it["description"])
                }[index?:0]

                binding.textView10.text = newsItem.title
                binding.textView6.text = newsItem.description

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
