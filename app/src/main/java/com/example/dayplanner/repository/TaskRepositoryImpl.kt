package com.example.dayplanner.repository

import com.example.dayplanner.model.TaskModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TaskRepositoryImpl : TaskRepository {

    var database: FirebaseDatabase = FirebaseDatabase.getInstance() //get firebase database instance
    var reference: DatabaseReference = database.reference.child("task") // For adding task item into the realtime database

    // Adding task into realtime database
    override fun addTask(taskModel: TaskModel, callback: (Boolean, String) -> Unit) {
        var id = reference.push().key.toString()
        taskModel.taskId = id
        reference.child(id).setValue(taskModel).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Task added Successfully")
            } else {
                callback (false, it.exception?.message.toString())
            }
        }
    }

    // Update the task in the database
    override fun updateTask(
        taskId: String, // takes the id of the task that is to be updated
        data: MutableMap<String, Any>, // the data that is updated
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(taskId).updateChildren(data).addOnCompleteListener {
            if (it.isSuccessful) {
                callback (true, "Task updated Successfully")
            } else {
                callback (false, it.exception?.message.toString())
            }
        }
    }

    // Delete the task from database
    override fun deleteTask(taskId: String, callback: (Boolean, String) -> Unit) {
        reference.child(taskId).removeValue().addOnCompleteListener { // Remove the task with taskId
            if (it.isSuccessful) {
                callback (true , "Task removed Successfully")
            } else {
                callback (false, it.exception?.message.toString())
            }
        }
    }

    // Get the particular task via taskId
    override fun getTaskById(taskId: String, callback: (TaskModel?, Boolean, String) -> Unit) {
        reference.child(taskId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var model = snapshot.getValue(TaskModel::class.java)
                    callback (model , true , "Data fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback (null, false, error.message)
            }

        })
    }

    // Get all task
    override fun getAllTask(callback: (List<TaskModel>?, Boolean, String) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener { // Change this
            override fun onDataChange(snapshot: DataSnapshot) {
                val tasks = mutableListOf<TaskModel>()
                if (snapshot.exists()) {
                    for (eachTask in snapshot.children) {
                        val model = eachTask.getValue(TaskModel::class.java)
                        model?.let { tasks.add(it) }
                    }
                    callback(tasks, true, "fetched")
                } else {
                    callback(emptyList(), true, "No data found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null, false, error.message)
            }
        })
    }

}