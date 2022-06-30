package com.example.gym_application.Model

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_application.Data.Exercises
import com.example.gym_application.Data.TypeOfExercise
import com.example.gym_application.R


class PairTypeOfExercisesAdapter(
    val pairs: List<Pair<Exercises, TypeOfExercise>>,
    val callback: (Pair<Exercises, TypeOfExercise>) -> Unit,
    val deleteCallBack: (Pair<Exercises, TypeOfExercise>)->Unit
) : RecyclerView.Adapter<PairTypeOfExercisesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PairTypeOfExercisesAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_exersise, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = pairs[position]
        holder.textView.text = currentItem.second.name
        holder.delete.visibility = View.VISIBLE

        holder.delete.setOnClickListener{
            deleteCallBack(currentItem)
        }
        holder.textView.setOnClickListener {
            callback(currentItem)
        }


    }

    override fun getItemCount(): Int {
        return pairs.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView
        val delete : Button

        init {
            // Define click listener for the ViewHolder's View.
            textView = itemView.findViewById(R.id.TextChange)
            delete = itemView.findViewById(R.id.delete)
        }

    }


}