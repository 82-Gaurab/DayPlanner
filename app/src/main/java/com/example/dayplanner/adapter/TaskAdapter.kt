package com.example.dayplanner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dayplanner.R
import com.example.dayplanner.model.TaskModel

class TaskAdapter (val context: Context, var data: ArrayList<TaskModel>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tName: TextView = itemView.findViewById(R.id.tvTaskName)
        val tDesc: TextView = itemView.findViewById(R.id.tvTaskDesc)
        val tTime: TextView = itemView.findViewById(R.id.tvTaskTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.sample_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.tName.text = data[position].taskTitle
        holder.tDesc.text = data[position].taskDesc
        holder.tTime.text = data[position].taskTime
    }

    fun updateData(tasks: List<TaskModel>){
        data.clear()
        data.addAll(tasks)
        notifyDataSetChanged() // updates in real time without reloading
    }


    fun getProductId (position: Int): String{
        return data[position].taskId
    }
}