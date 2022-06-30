package com.example.gym_application.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.gym_application.Data.*
import com.example.gym_application.databinding.FragmentexercisedisplayBinding

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class FragmentExerciseDisplay : Fragment() {
    private var exerciselist: List<Exercises>? = null
    private lateinit var binding: FragmentexercisedisplayBinding
    private val userViewModel: UserViewModel by viewModels {
        Factory((requireActivity().application as UserApplication).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentexercisedisplayBinding.inflate(inflater, container, false)

        var exerciseDetails = arguments?.getSerializable("exercise") as TypeOfExercise
        var exerciseId = arguments?.getInt("excerciseId")
        binding.textView11.text = exerciseDetails.name
        binding.description.text = exerciseDetails.description

        if (exerciseDetails.weight > 0){
        binding.weights.visibility = View.VISIBLE

        }




        binding.edit.hint = exerciseDetails.reps.toString()


        binding.addToList.setOnClickListener{


            exerciselist?.find { it.id == exerciseId }?.let { exerciseFind ->
                var type = exerciseFind.list.find { it.name == exerciseDetails.name }

                type?.selected =true
                userViewModel.addUser(exerciseFind)
                Toast.makeText(requireContext(),"Added ${type?.name} to list",Toast.LENGTH_SHORT).show()

            }
        }



        binding.button4.setOnClickListener {


            exerciselist?.find { it.id == exerciseId }?.let { exerciseFind ->
                var type = exerciseFind.list.find { it.name == exerciseDetails.name }


                type?.reps = binding.edit.text.toString().toInt()
                userViewModel.addUser(exerciseFind)
                showData()


            }
        }




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.allWords.observe(viewLifecycleOwner) {
            exerciselist = it

        }

    }

    fun showData() {

        binding.edit.hint = binding.edit.text

    }
}




