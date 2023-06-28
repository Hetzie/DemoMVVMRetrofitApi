package com.excellentwebworld.demomvvmretrofitapi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.excellentwebworld.demomvvmretrofitapi.databinding.EntryCustomListBinding

class EntryAdapter : RecyclerView.Adapter<EntryAdapter.ViewHolder>() {
    var entryList = mutableListOf<EntryDetails>()

    inner class ViewHolder(private val binding: EntryCustomListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.model = entryList[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = EntryCustomListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(user: List<EntryDetails>){
        this.entryList = user.toMutableList()
        notifyDataSetChanged()
    }
}