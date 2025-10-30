package com.example.dmot.Activity

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dmot.R
import com.example.dmot.databinding.ActivityBookingsBinding
import java.util.Calendar

class BookingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookingsBinding



    // Email address of the admin
    private val adminEmail = "otadmin@dmot.co.za"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Dropdown for OT Names ---
        val otNames = listOf("Adita", "Andani", "Anele", "Kayla", "Shamim")
        val otAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, otNames)
        otAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.otSpinner.adapter = otAdapter

        // --- Bottom Navigation Setup ---
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

        // --- Date Picker Setup ---
        binding.appointmentDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "%02d/%02d/%d".format(selectedDay, selectedMonth + 1, selectedYear)
                binding.appointmentDate.setText(formattedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        // --- ✅ Submit Button OnClickListener ---
        binding.btnSubmit.setOnClickListener {
            val name = binding.etPatientName.text.toString().trim()
            val termsChecked = binding.cbTerms.isChecked

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter the patient's name", Toast.LENGTH_SHORT).show()
            } else if (!termsChecked) {
                Toast.makeText(this, "Please accept the Terms & Conditions", Toast.LENGTH_SHORT).show()
            } else {
                val selectedOT = binding.otSpinner.selectedItem.toString()
                val date = binding.appointmentDate.text.toString()

                val message = "Appointment booked for $name with $selectedOT on $date"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                // Send email with booking details
                sendBookingEmail(name, selectedOT, date)
            }
        }
    }

    //This code was taken and adapted from Youtube
    //Link: https://www.youtube.com/watch?v=4WKCMAbu7ZI
    //Author: @techblax
    //Author Link: https://www.youtube.com/@techblax

    //This code was taken and adapted from Youtube
    //Link: https://www.youtube.com/watch?v=GOqv3jnw_Go
    //Author: @kbcoder8788
    //Author Link: https://www.youtube.com/@kbcoder8788

    // Function to send email
    private fun sendBookingEmail(patientName: String, selectedOT: String, appointmentDate: String) {
        val subject = "New Appointment Booking"
        val body = """
            New appointment booking details:
            
            Patient Name: $patientName
            Occupational Therapist: $selectedOT
            Appointment Date: $appointmentDate
            
            Please confirm and add to the schedule.
        """.trimIndent()

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(adminEmail))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        // Check if an email client is available
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(emailIntent, "Send email via..."))
        } else {
            Toast.makeText(this, "No email client installed. Please install one to send the booking.", Toast.LENGTH_SHORT).show()
        }
    }
}