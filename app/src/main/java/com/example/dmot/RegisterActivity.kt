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
            val name = binding.registerName.text.toString().trim()
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()
            val confirmPassword = binding.registerConfirmPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                binding.registerNameLayout.error = "Please enter your name"
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

                val user = User(name, email, password, confirmPassword)

                database.child(emailKey).setValue(user)
                    .addOnSuccessListener {
                        val homeIntent = Intent(this@RegisterActivity, HomeActivity::class.java)
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(homeIntent)
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        // Display error message to user
                        binding.registerEmailLayout.error =
                            "Registration failed: ${exception.message}"

                        // Optionally log the error
                        android.util.Log.e("RegisterActivity", "Registration failed", exception)
                    }
            }
        }
    }
}