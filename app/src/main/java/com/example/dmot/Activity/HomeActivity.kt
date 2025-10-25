package com.example.dmot.Activity

import android.os.Bundle
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dmot.Adapter.DoctorsAdapter
import com.example.dmot.R
import com.example.dmot.ViewModel.MainViewModel
import com.example.dmot.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel = MainViewModel()

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
                binding.textView2.text = "Hi $name"
            }
        }

        // Bottom navigation setup
        val bottomNav = binding.bottomNavView
        bottomNav.selectedItemId = R.id.home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> true
                R.id.bookings -> {
                    startActivity(Intent(this, BookingsActivity::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        initDoctor()
    }

    //
    private fun initDoctor() {
        binding.apply {
            viewModel.loadDoctors().observe(this@HomeActivity, {
                topView.layoutManager =
                    LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
                topView.adapter = DoctorsAdapter(it)
            })
        }
    }
}
