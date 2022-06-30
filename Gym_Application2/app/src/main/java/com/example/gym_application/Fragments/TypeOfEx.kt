package com.example.gym_application.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.window.isPopupLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gym_application.Data.*
import com.example.gym_application.R
import com.example.gym_application.databinding.FragmentAddAnExerciseBinding
import com.example.gym_application.databinding.FragmentTypeOfExerciseBinding


class TypeOfEx : Fragment() {
    private lateinit var binding: FragmentTypeOfExerciseBinding
    private val userViewModel: UserViewModel by viewModels {
        Factory((requireActivity().application as UserApplication).repository)
    }
    private var exerciselist : List<Exercises>? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTypeOfExerciseBinding.inflate(inflater, container, false)

        var exerciseId = arguments?.getInt("excerciseId")






        binding.Submit.setOnClickListener{

            exerciselist?.find { it.id == exerciseId }?.let { exerciseFind->
                val typeofexercise = exerciseFind.list

                val name = binding.editText1.text.toString()
                val description = binding.editText2.text.toString()
                val Reps = binding.editText3.text.toString().toInt()



                val newType = TypeOfExercise(name, description,Reps,0)

                typeofexercise.add(newType)
                exerciseFind.list = typeofexercise
                userViewModel.addUser(exerciseFind)

            }


            findNavController().popBackStack()

        }





return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.allWords.observe(viewLifecycleOwner) {
            exerciselist = it

        }
        }
}