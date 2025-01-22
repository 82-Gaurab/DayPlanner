package com.example.dayplanner.repository

import com.example.dayplanner.model.TaskModel

interface TaskRepository {
    fun addTask(taskModel: TaskModel, callback:(Boolean, String) -> Unit)

    fun updateTask(taskId:String, data: MutableMap<String,Any>, callback: (Boolean, String) -> Unit)

    fun deleteTask(taskId: String, callback: (Boolean, String) -> Unit)

    fun getTaskById(taskId: String, callback: (TaskModel?, Boolean, String) -> Unit)

    fun getAllTask(callback: (List<TaskModel>?,Boolean, String) -> Unit)
}