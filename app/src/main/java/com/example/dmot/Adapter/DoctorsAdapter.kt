package com.example.dmot.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dmot.Model.Doctor
import com.example.dmot.databinding.TemplateDoctorBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.dmot.Activity.DoctorActivity

// This method was adapted from YouTube
// https://www.youtube.com/watch?v=rsK4UXqs6Lk

class DoctorsAdapter(val items: MutableList<Doctor>): RecyclerView.Adapter<DoctorsAdapter.Viewholder>() {
    private var context : Context? = null
    class Viewholder(val binding: TemplateDoctorBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorsAdapter.Viewholder {
        context = parent.context
        return Viewholder(TemplateDoctorBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: DoctorsAdapter.Viewholder, position: Int) {
        holder.binding.nameTxt.text = items[position].Name
        holder.binding.specialTxt.text = items[position].Speciality

        Glide.with(holder.itemView.context)
            .load(items[position].Picture)
            .apply { RequestOptions().transform(CenterCrop()) }
            .into(holder.binding.img)

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, DoctorActivity::class.java)
            intent.putExtra("object", items[position])
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

}