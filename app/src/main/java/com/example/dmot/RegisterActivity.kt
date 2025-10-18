package com.example.dmot

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dmot.databinding.ActivityLoginBinding
import com.example.dmot.databinding.ActivityRegisterBinding
import com.example.dmot.models.User
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("users")

        binding.textLogin.setOnClickListener {
            val registerIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(registerIntent)
        }

        binding.homeBtnRegister.setOnClickListener {
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()
            val confirmPassword = binding.registerConfirmPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                binding.registerEmailLayout.error = "Please enter your email"
                binding.registerPasswordLayout.error = "Please enter your password"
                binding.registerConfirmPasswordLayout.error = "Please enter your password"
                return@setOnClickListener
            } else if (password != confirmPassword) {
                binding.registerConfirmPasswordLayout.error = "Passwords do not match"
                return@setOnClickListener
            } else {
                val emailKey = email.replace(".", ",")
                val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                sharedPref.edit().putString("emailKey", emailKey).apply()

                val user = User(email, password, confirmPassword)
            }
        }
    }
}