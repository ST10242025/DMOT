package com.example.dmot.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dmot.Model.Doctor
import com.example.dmot.Repository.MainRepository

class MainViewModel() : ViewModel() {
    private val repository = MainRepository()

    fun loadDoctors(): LiveData<MutableList<Doctor>> {
        return repository.load()
    }
}