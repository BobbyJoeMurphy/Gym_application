package com.example.gym_application.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gym_application.Data.*
import com.example.gym_application.Model.PairTypeOfExercisesAdapter
import com.example.gym_application.Model.TypeOfExercisesAdapter
import com.example.gym_application.R
import com.example.gym_application.databinding.FragmentExersiseBinding
import com.example.gym_application.databinding.FragmentWorkOutBinding
import kotlinx.coroutines.selects.select


class WorkOut_Fragment : Fragment() {
    private lateinit var binding: FragmentWorkOutBinding
    private val userViewModel: UserViewModel by viewModels {
        Factory((requireActivity().application as UserApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorkOutBinding.inflate(inflater, container, false)







        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        userViewModel.allWords.observe(viewLifecycleOwner) { exercises ->

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
            binding.type.adapter = PairTypeOfExercisesAdapter(selectedTypes, deleteCallBack = { click->
                click.second.selected = false
                userViewModel.addUser(click.first)
            }, callback =  { clickedItem ->
                findNavController().navigate(
                    R.id.action_WorkOut_to_Triceps,
                    bundleOf(
                        "exercise" to clickedItem.second,
                        "excerciseId" to clickedItem.first.id
                    )
                )
            })
        }


    }

}