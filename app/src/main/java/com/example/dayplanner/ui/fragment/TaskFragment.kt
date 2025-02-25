package com.example.dayplanner.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dayplanner.R
import com.example.dayplanner.adapter.TaskAdapter
import com.example.dayplanner.databinding.FragmentTaskBinding
import com.example.dayplanner.repository.TaskRepositoryImpl
import com.example.dayplanner.viewmodel.TaskViewModel
import com.example.dayplanner.ui.activity.AddTaskActivity
import java.util.ArrayList

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TaskAdapter
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskAdapter(requireContext(), ArrayList())

//        Set up recycler view
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext()) // Set layout manager
        binding.taskRecyclerView.adapter = adapter // Set adapter

//        Initialize viewModel
        val repo = TaskRepositoryImpl()
        taskViewModel = TaskViewModel(repo)

        taskViewModel.getAllTask()

        taskViewModel.allTask.observe(viewLifecycleOwner)
        { tasks ->
            tasks?.let {
                adapter.updateData(it)
            }
        }

        binding.addTaskFloatingBtn.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
