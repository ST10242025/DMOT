package com.example.dmot

import android.os.Bundle
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

        }
    }
