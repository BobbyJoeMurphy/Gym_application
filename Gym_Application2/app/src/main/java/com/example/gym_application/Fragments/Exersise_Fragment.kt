package com.example.gym_application.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.gym_application.Data.*
import com.example.gym_application.Model.RecycleAdapter

import com.example.gym_application.R


import com.example.gym_application.databinding.FragmentExersiseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class Exersise_Fragment : Fragment() {
    private lateinit var binding: FragmentExersiseBinding
    private val userViewModel: UserViewModel by viewModels {
        Factory((requireActivity().application as UserApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExersiseBinding.inflate(inflater, container, false)

        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            UserRoomDatabase::class.java, "Exercises_Table"
        ).allowMainThreadQueries()
            .build()








        lifecycleScope.launch {
            val list = db.userDao().readAllData().first()
            gridList(list)
        }

        binding.FAB.setOnClickListener{

            findNavController().navigate(R.id.action_Exersise_Fragment_to_addAnExercise)


        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.allWords.observe(viewLifecycleOwner) {
            gridList(it)
        }
    }

    //gridlist taking a list of exercises launches a Main dispatchers thread binding the addapter to a list. call back data in  {currentitem}
    fun gridList(list: List<Exercises>) {

            binding.gridRecycler.adapter = RecycleAdapter(list) {clickedItem->
                findNavController().navigate(
                    R.id.action_Exersise_Fragment_to_fragment_Arms,
                    bundleOf("exercise" to clickedItem)
                )
            }
            (binding.gridRecycler.layoutManager as GridLayoutManager).spanCount = 3
        }

    }



