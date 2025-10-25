package com.example.dmot.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dmot.Model.Doctor
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun load(): LiveData<MutableList<Doctor>> {
        val listData = MutableLiveData<MutableList<Doctor>>()
        val ref = firebaseDatabase.getReference("Doctors")
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<Doctor>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(Doctor::class.java)
                    item?.let { lists.add(it) }
                }
                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return listData
    }
}