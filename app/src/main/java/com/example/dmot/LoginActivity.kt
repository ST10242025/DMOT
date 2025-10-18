package com.example.dmot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dmot.databinding.ActivityLoginBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("users")

        binding.textRegister.setOnClickListener {
            val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(registerIntent)
        }

        binding.homeBtnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                binding.loginEmailLayout.error = "Email is required"
                binding.loginPasswordLayout.error = "Password is required"
                return@setOnClickListener
            } else {
                val emailKey = email.replace(".", ",")
                val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                sharedPref.edit().putString("emailKey", emailKey).apply()

                database.child(emailKey).get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            val userPassword = snapshot.child("password").value.toString()

                            if (userPassword == password) {
                                val calculationIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(calculationIntent)
                            } else {
                                binding.loginPasswordLayout.error = "Incorrect password"
                            }
                        } else {
                            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}