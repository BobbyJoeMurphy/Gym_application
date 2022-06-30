package com.example.gym_application.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_application.Data.Exercises
import com.example.gym_application.R
import kotlinx.coroutines.flow.Flow


class RecycleAdapter(val list: List<Exercises>, val callback: (Exercises) -> Unit) :  RecyclerView.Adapter<RecycleAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_cards,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textView.text = currentItem.name
        holder.imageView.setImageResource(currentItem.image)
        holder.itemView.setOnClickListener{
            callback(currentItem)



        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = itemView.findViewById(R.id.name)
            imageView = itemView.findViewById(R.id.imagev)
        }

    }




}








