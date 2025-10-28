package com.example.dmot.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dmot.R
import com.example.dmot.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

    // Save Button Functionality
    binding.btnSave.setOnClickListener {
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val address = binding.etAddress.text.toString()

        Toast.makeText(this, "Profile updated for $username", Toast.LENGTH_SHORT).show()
    }


        // --- Bottom Navigation Setup ---
        val bottomNav = binding.bottomNavView
        bottomNav.selectedItemId = R.id.home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.bookings -> {
                    startActivity(Intent(this, BookingsActivity::class.java))
                    true
                }
                R.id.profile -> true
                else -> false
            }
        }
}}