package com.example.dayplanner.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dayplanner.R
import com.example.dayplanner.adapter.TaskAdapter
import com.example.dayplanner.databinding.ActivityTaskDashboardBinding
import com.example.dayplanner.model.TaskModel
import com.example.dayplanner.repository.TaskRepositoryImpl
import com.example.dayplanner.viewmodel.TaskViewModel

class TaskDashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityTaskDashboardBinding
    lateinit var adapter: TaskAdapter

    lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTaskDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(repo)

        taskViewModel.getAllTask()

        taskViewModel.allTasks.observe(this) {
            product ->
            product?.let {
                adapter.updateData(it)
            }
        }

        binding.addTaskFloatingBtn.setOnClickListener {
            startActivity(Intent(this@TaskDashboardActivity, AddTaskActivity::class.java))
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editTaskDesc)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}