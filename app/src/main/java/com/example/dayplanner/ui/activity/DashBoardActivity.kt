package com.example.dayplanner.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.dayplanner.R
import com.example.dayplanner.adapter.TaskAdapter
import com.example.dayplanner.databinding.ActivityDashBoardBinding
import com.example.dayplanner.ui.fragment.ProfileFragment
import com.example.dayplanner.ui.fragment.TaskFragment
import com.example.dayplanner.viewmodel.TaskViewModel
import java.util.ArrayList

class DashBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashBoardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        replaceFragment(TaskFragment())

        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.navTask -> {replaceFragment(TaskFragment())}
                R.id.navProfile -> {replaceFragment(ProfileFragment())}
                else -> {}
            }
            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragmentFrame , fragment)
        fragmentTransaction.commit()
    }
}