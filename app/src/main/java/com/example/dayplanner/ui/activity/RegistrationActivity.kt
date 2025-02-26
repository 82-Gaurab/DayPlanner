package com.example.dayplanner.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dayplanner.R
import com.example.dayplanner.databinding.ActivityRegistrationBinding
import com.example.dayplanner.model.UserModel
import com.example.dayplanner.repository.UserRepositoryImp
import com.example.dayplanner.viewmodel.UserViewModel

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userRepository = UserRepositoryImp()
        userViewModel = UserViewModel(userRepository)

        binding.btnRegister.setOnClickListener {
            var email: String = binding.textEmail.text.toString()
            var password: String = binding.inpPassword.text.toString()
            var fName: String = binding.inpFirstName.text.toString()
            var lName: String = binding.inpLastName.text.toString()
            var contact: String = binding.inpContact.text.toString()

            userViewModel.signup(email, password) { success, message, userId ->
                if (success) {
                    val userModel = UserModel(
                        userId,
                        email, fName, lName, contact
                    )
                    addUser(userModel)
                } else {
                    Toast.makeText(
                        this@RegistrationActivity,
                        message, Toast.LENGTH_SHORT
                    ).show()
                }
            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editTaskDesc)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        binding.redirectLogin.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
        }
    }

    fun addUser(userModel: UserModel) {
        userViewModel.addUserToDatabase(userModel.userId, userModel) { success, message ->
            if (success) {
                Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
