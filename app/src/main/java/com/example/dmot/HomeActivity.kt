package com.example.dmot

import android.os.Bundle
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AppCompatActivity
import com.example.dmot.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val emailKey = sharedPref.getString("emailKey", null)
        if (emailKey != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference("users")
            dbRef.child(emailKey).get().addOnSuccessListener { snapshot ->
                val name = snapshot.child("name").getValue(String::class.java) ?: "User"
                binding.textView3.text = "Hi $name"
            }
        }

        // Bottom navigation setup
        val bottomNav = binding.bottomNavView
        bottomNav.selectedItemId = R.id.home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> true
                R.id.booking -> {
                    startActivity(Intent(this, BookingsActivity::class.java))
                    true
                }
                R.id.settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
    }
