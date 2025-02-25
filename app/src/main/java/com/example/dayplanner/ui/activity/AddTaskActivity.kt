package com.example.dayplanner.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
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

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inpTaskDetail)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    @SuppressLint("DefaultLocale")
    private fun addTask(){
        var taskTitle = binding.inpTaskTitle.text.toString()
        var taskDesc = binding.inpTaskDetail.text.toString()

        // Fetch time from TimePicker
        val hour = binding.timePicker.hour
        val minute = binding.timePicker.minute

        // Convert hour and minute to a formatted time string
        val taskTime = String.format("%02d:%02d", hour, minute)

        var model = TaskModel(
            "",
            taskTitle,
            taskDesc,
            taskTime
        )

        taskViewModel.addTask(model) {success , message ->
            if (success) {
                Toast.makeText(this@AddTaskActivity, message, Toast.LENGTH_LONG).show()
                startActivity(Intent(this@AddTaskActivity, DashBoardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@AddTaskActivity, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}