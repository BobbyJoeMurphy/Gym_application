package com.example.gym_application.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_application.R

class NewsAdapter(val list: List<Feed>, val callback: (Int) -> Unit) :  RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_card,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.itemView.setOnClickListener{
            callback(position)



        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val description: TextView
        val title: TextView

        init {
            // Define click listener for the ViewHolder's View.
            title = itemView.findViewById(R.id.textview5)
            description = itemView.findViewById(R.id.textView7)
        }

    }




}

