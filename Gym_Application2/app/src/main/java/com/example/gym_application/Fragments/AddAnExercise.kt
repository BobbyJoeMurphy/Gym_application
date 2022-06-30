package com.example.gym_application.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.gym_application.Data.*
import com.example.gym_application.R
import com.example.gym_application.databinding.FragmentAddAnExerciseBinding
import com.example.gym_application.databinding.FragmentExersiseBinding
import kotlin.reflect.typeOf


class AddAnExercise : Fragment() {
    private lateinit var binding: FragmentAddAnExerciseBinding
    private val userViewModel: UserViewModel by viewModels {
        Factory((requireActivity().application as UserApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddAnExerciseBinding.inflate(inflater, container, false)








        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.Submit.setOnClickListener {
            val name = binding.Name.text.toString()


            userViewModel.addUser(
                Exercises(
                    0,
                    name,
                    R.drawable.common_full_open_on_phone,
                    arrayListOf()
                )
            )
            findNavController().popBackStack()


        }
    }

}