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
        var taskDesc = binding.inpTaskDesc.text.toString()

        // Fetch time from TimePicker
        val hour = binding.timePicker.hour
        val minute = binding.timePicker.minute

// Determine AM or PM
        val amPm = if (hour < 12) "AM" else "PM"

// Convert to 12-hour format
        val formattedHour = if (hour % 12 == 0) 12 else hour % 12

// Format the time string
        val taskTime = String.format("%02d:%02d %s", formattedHour, minute, amPm)

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