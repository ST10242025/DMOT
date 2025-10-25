package com.example.dmot.Model

import android.os.Parcel
import android.os.Parcelable

data class Doctor(
    val Address: String = "",
    val Biography: String = "",
    val Skills: List<String> = emptyList(),
    val Id: Int = 0,
    val Name: String = "",
    val Picture: String = "",
    val Speciality: String = "",
    val Email: String = "",
    val Phone: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()

    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(Address)
        p0.writeString(Biography)
        p0.writeStringList(Skills)
        p0.writeInt(Id)
        p0.writeString(Name)
        p0.writeString(Picture)
        p0.writeString(Speciality)
        p0.writeString(Email)
        p0.writeString(Phone)
    }

    companion object CREATOR : Parcelable.Creator<Doctor> {
        override fun createFromParcel(parcel: Parcel): Doctor {
            return Doctor(parcel)
        }

        override fun newArray(size: Int): Array<Doctor?> {
            return arrayOfNulls(size)
        }
    }
}
