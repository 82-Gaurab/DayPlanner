package com.example.dayplanner.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dayplanner.R
import com.example.dayplanner.databinding.ActivityForgotPasswordBinding
import com.example.dayplanner.repository.UserRepositoryImp
import com.example.dayplanner.utils.LoadingUtils
import com.example.dayplanner.viewmodel.UserViewModel

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val userRepository = UserRepositoryImp()
        val userViewModel = UserViewModel(userRepository)


        binding.btnSubmit.setOnClickListener {
            loadingUtils.show();
            var email: String = binding.inpEmail.text.toString();

            userViewModel.forgetPassword(email) {
                    success, message ->
                if (success) {
                    loadingUtils.dismiss()
                    Toast.makeText(this@ForgotPasswordActivity, message, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
                } else {
                    loadingUtils.dismiss()
                    Toast.makeText(this@ForgotPasswordActivity, message, Toast.LENGTH_LONG).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editTaskDesc)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}