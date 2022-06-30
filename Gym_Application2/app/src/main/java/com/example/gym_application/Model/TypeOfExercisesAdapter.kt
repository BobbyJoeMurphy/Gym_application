package com.example.gym_application.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_application.Data.Exercises
import com.example.gym_application.Data.TypeOfExercise
import com.example.gym_application.R


class TypeOfExercisesAdapter(val list: List<TypeOfExercise>, val callback: (TypeOfExercise) -> Unit) :  RecyclerView.Adapter<TypeOfExercisesAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeOfExercisesAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_exersise,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView.text = currentItem.name
        holder.textView.setOnClickListener{
            callback(currentItem)



        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = itemView.findViewById(R.id.TextChange)
        }

    }




}






