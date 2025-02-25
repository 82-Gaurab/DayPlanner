package com.example.dayplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dayplanner.model.TaskModel
import com.example.dayplanner.repository.TaskRepository

class TaskViewModel (val repository: TaskRepository) {
    fun addTask(taskModel: TaskModel, callback:(Boolean, String) -> Unit){
        repository.addTask(taskModel , callback)
    }

    fun updateTask(taskId:String, data: MutableMap<String,Any>, callback: (Boolean, String) -> Unit){
        repository.updateTask(taskId , data, callback)
    }

    fun deleteTask(taskId: String, callback: (Boolean, String) -> Unit){
        repository.deleteTask(taskId, callback)
    }

    var _tasks = MutableLiveData<TaskModel>()
    var task = MutableLiveData<TaskModel>()
        get() = _tasks //getter

    var _allTasks = MutableLiveData<List<TaskModel>?>()
    var allTask = MutableLiveData<List<TaskModel>?>()
        get() = _allTasks

    fun getTaskById(taskId: String){
        repository.getTaskById(taskId) {
            task, success, message ->
            if (success) {
                _tasks.value = task
            }
        }
    }

    fun getAllTask(){
        repository.getAllTask() {
                allTask, success, message ->
            if (success) {
                _allTasks.value = allTask
            }
        }
    }


}