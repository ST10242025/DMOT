package com.example.dmot.models

data class User (
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)