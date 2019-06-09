package com.example.withpet.ui.hospital

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.vo.HospitalSearchDTO

class HospitalSearchRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var searchList : ArrayList<HospitalSearchDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.activity_main,parent,false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = searchList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    private inner class CustomViewHolder (var view : View) : RecyclerView.ViewHolder(view)
}