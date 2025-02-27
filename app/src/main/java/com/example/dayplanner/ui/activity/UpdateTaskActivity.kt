package com.example.dayplanner.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dayplanner.R
import com.example.dayplanner.databinding.ActivityUpdateTaskBinding
import com.example.dayplanner.repository.TaskRepositoryImpl
import com.example.dayplanner.utils.LoadingUtils
import com.example.dayplanner.viewmodel.TaskViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class UpdateTaskActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateTaskBinding
    lateinit var taskViewModel: TaskViewModel
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val repo = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(repo)

        var taskId:String? = intent.getStringExtra("taskId")

        taskViewModel.getTaskById(taskId.toString())

        taskViewModel.task.observe(this) { task ->
            binding.updateTaskTitle.setText(task?.taskTitle ?: "")
            binding.updateTaskDesc.setText(task?.taskDesc ?: "")

            // Assuming taskTime is stored as "hh:mm a" (e.g., "10:00 PM")
            task?.taskTime?.let { time ->
                val inputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()) // 12-hour format with AM/PM
                val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault()) // 24-hour format

                try {
                    val date = inputFormat.parse(time) // Convert to Date object
                    val formattedTime = outputFormat.format(date) // Convert to "HH:mm" (24-hour format)

                    val parts = formattedTime.split(":")
                    if (parts.size == 2) {
                        val hour = parts[0].toInt()
                        val minute = parts[1].toInt()

                        // Set TimePicker values
                        binding.updateTimePicker.hour = hour  // For API 23+
                        binding.updateTimePicker.minute = minute
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }

        binding.updateBtn.setOnClickListener {
            loadingUtils.show()
            val taskTitle = binding.updateTaskTitle.text.toString()
            val taskDesc = binding.updateTaskDesc.text.toString()

            // Fetch time from TimePicker
            val hour = binding.updateTimePicker.hour
            val minute = binding.updateTimePicker.minute

            val amPm = if (hour < 12) "AM" else "PM"

            val formattedHour = if (hour % 12 == 0) 12 else hour % 12

            val taskTime = String.format("%02d:%02d %s", formattedHour, minute, amPm)

            var updateMap = mutableMapOf<String, Any>()

            updateMap["taskTitle"] = taskTitle
            updateMap["taskDesc"] = taskDesc
            updateMap["taskTime"] = taskTime

            taskViewModel.updateTask(taskId.toString() , updateMap){
                success, message ->
                if(success) {
                    loadingUtils.dismiss()
                    Toast.makeText(this@UpdateTaskActivity, message.toString(), Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    loadingUtils.dismiss()
                    Toast.makeText(this@UpdateTaskActivity, message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}