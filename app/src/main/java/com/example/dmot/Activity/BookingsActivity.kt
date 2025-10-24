package com.example.dmot.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dmot.R
import com.example.dmot.databinding.ActivityBookingsBinding


private lateinit var binding: ActivityBookingsBinding
class BookingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bottom navigation setup
        val bottomNav = binding.bottomNavView
        bottomNav.selectedItemId = R.id.home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.bookings -> true
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }
}