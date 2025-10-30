package com.example.dmot.Activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dmot.R


//This code was taken and adapted from Youtube
//Link: https://www.youtube.com/watch?v=K4CGYiQu52s
//Author: @CodesEasy
//Author Link: https://www.youtube.com/@CodesEasy

class InstructionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_instruction)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val backButton = findViewById<ImageButton>(R.id.imageButton)
        backButton.setOnClickListener { finish() }


        val container = findViewById<LinearLayout>(R.id.stepsContainer)

        //This code was taken and adapted from geeksforgeeks
        //Link: https://www.geeksforgeeks.org/android/android-listview-in-kotlin
        //Author: praveenruhil
        //Author Link: https://www.geeksforgeeks.org/user/praveenruhil

        val items = listOf(
            "To Make An Appointment follow the Nine steps below:",
            "First: Select an OT of your choice in the Home page And then scroll to the bottom of the screen and Click the Make Appointment Button",
            "Second: Fill in the Full Name of the patient.",
            "Third: Fill in the Address the patient wants the OT to visit.",
            "Fourth: Provide the patient's Medical Aid Number",
            "Sixth: Full Name of the Guarantor",
            "Seventh: Select a date for the appointment (To be confirmed By the Admin)",
            "Eighth: Select your OT by name in the dropdown menu.",
            "Ninth: Read And Accept the T and C the check the box to Accept.",
            "Tenth: Click Submit."
        )

        items.forEachIndexed { index, text ->
            val tv = TextView(this).apply {
                this.text = "${index + 1}. $text"
//                setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Subtitle1)
                setPadding(0, 24, 0, 24)
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
                setTextSize(25f)


            }
            container.addView(tv)
        }
    }
}