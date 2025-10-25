package com.example.dmot.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dmot.Model.Doctor
import com.example.dmot.R
import com.example.dmot.databinding.ActivityDoctorBinding

// This method was adapted from YouTube
// https://www.youtube.com/watch?v=rsK4UXqs6Lk

class DoctorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDoctorBinding
    private lateinit var  item : Doctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AppointmentBtn.setOnClickListener {
            val intent = Intent(this, BookingsActivity::class.java)
            startActivity(intent)
        }

        getBundle()
    }

    private fun  getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.apply {
            titleTxt.text = item.Name
            specialTxt.text = item.Speciality
            addressTxt.text = item.Address
            biographyTxt.text = item.Biography
            numberTxt.text = item.Phone
            emailTxt.text = item.Email

            // Clear any existing views
            skillsContainer.removeAllViews()

            // Suppose item.Skills is a List<String>
            item.Skills?.forEach { skill ->
                val textView = TextView(this@DoctorActivity)
                textView.text = "\u2022 $skill" // Bullet point
                textView.textSize = 16f
                textView.setTextColor(ContextCompat.getColor(this@DoctorActivity, R.color.black))
                textView.setPadding(16, 8, 0, 8) // optional spacing
                skillsContainer.addView(textView)
            }

            backBtn.setOnClickListener { finish() }

            Glide.with(this@DoctorActivity)
                .load(item.Picture)
                .into(profileImg)
        }
    }
}