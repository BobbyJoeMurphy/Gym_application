package com.example.gym_application.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gym_application.Data.Exercises
import com.example.gym_application.Data.Factory
import com.example.gym_application.Data.UserApplication
import com.example.gym_application.Data.UserViewModel
import com.example.gym_application.Model.RecycleAdapter
import com.example.gym_application.Model.TypeOfExercisesAdapter
import com.example.gym_application.R

import com.example.gym_application.databinding.FragmentTricepsBinding
import java.io.Serializable


class FragmentListExercises : Fragment() {
    private lateinit var binding: FragmentTricepsBinding
    private val userViewModel: UserViewModel by viewModels {
        Factory((requireActivity().application as UserApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTricepsBinding.inflate(inflater, container, false)

        arguments?.getSerializable("exercise")



        binding.floatingActionButton.setOnClickListener {
            val exercise: Exercises = arguments?.getSerializable("exercise") as Exercises
            findNavController().navigate(
                R.id.action_Fragment_Triceps_to_typeOfExercise,
                bundleOf(
                    "excerciseId" to exercise.id
                )
            )
        }





        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.allWords.observe(viewLifecycleOwner){exersiseList->
            val exerciseId =(arguments?.getSerializable("exercise") as Exercises).id

            exersiseList.find {
                it.id == exerciseId
            }?.let {Exercise->
                binding.recyclerView.adapter = TypeOfExercisesAdapter(Exercise.list) {
                    findNavController().navigate(
                        R.id.action_Fragment_Triceps_to_Triceps,
                        bundleOf(
                            "exercise" to it,
                            "excerciseId" to Exercise.id
                        )
                    )
                }


            }


        }


    }

}




