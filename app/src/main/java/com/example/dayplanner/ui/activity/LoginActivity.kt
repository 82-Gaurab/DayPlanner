package com.example.dayplanner.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dayplanner.R
import com.example.dayplanner.databinding.ActivityLoginBinding
import com.example.dayplanner.repository.UserRepositoryImp
import com.example.dayplanner.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = UserRepositoryImp()
        val userViewModel = UserViewModel(userRepository)

        binding.btnLogin.setOnClickListener {
            var email: String = binding.editTextEmail.text.toString()
            var password: String = binding.editTextPassword.text.toString()

            userViewModel.login(email, password) {
                    success, message ->
                if (success) {
                    Toast.makeText(this@LoginActivity,message, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginActivity,message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.redirectRegistration.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }

        binding.redirectForgetPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editTaskDesc)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}