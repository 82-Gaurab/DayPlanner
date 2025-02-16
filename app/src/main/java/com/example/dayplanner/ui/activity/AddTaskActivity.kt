package com.example.dayplanner.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dayplanner.R
import com.example.dayplanner.databinding.ActivityAddTaskBinding
import com.example.dayplanner.model.TaskModel
import com.example.dayplanner.repository.TaskRepositoryImpl
import com.example.dayplanner.viewmodel.TaskViewModel

class AddTaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddTaskBinding

    lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(repo)

        binding.addBtn.setOnClickListener {
            addTask()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editTaskDesc)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addTask(){
        var taskTitle = binding.editTaskTitle.text.toString()
        var taskDesc = binding.editTaskDesc.text.toString()

        var model = TaskModel(
            "",
            taskTitle,
            taskDesc
        )

        taskViewModel.addTask(model) {success , message ->
            if (success) {
                Toast.makeText(this@AddTaskActivity, message, Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this@AddTaskActivity, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}