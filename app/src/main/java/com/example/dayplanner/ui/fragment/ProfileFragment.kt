package com.example.dayplanner.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dayplanner.R
import com.example.dayplanner.databinding.FragmentProfileBinding
import com.example.dayplanner.model.UserModel
import com.example.dayplanner.repository.UserRepositoryImp
import com.example.dayplanner.ui.activity.AddTaskActivity
import com.example.dayplanner.ui.activity.LoginActivity
import com.example.dayplanner.viewmodel.UserViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var userViewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repository = UserRepositoryImp()
        userViewModel = UserViewModel(repository)

        var currentUser =userViewModel.getCurrentUser()

        currentUser.let {
            userViewModel.getUserFromDatabase(it?.uid.toString())
        }

        userViewModel.userData.observe(requireActivity()) { user ->
            binding.profileUsername.text =user?.firstName + " " + user?.lastName
            binding.profileEmail.text =user?.email
        }

        binding.cardLogOut.setOnClickListener {
            userViewModel.logout { success, message ->
                if (success) {
                    // Handle successful logout (e.g., navigate to login screen)
                    Toast.makeText(context, "Logout successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                } else {
                    // Handle logout failure (e.g., show error message)
                    Toast.makeText(context, "Logout failed: $message", Toast.LENGTH_SHORT).show()
                }
            }
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