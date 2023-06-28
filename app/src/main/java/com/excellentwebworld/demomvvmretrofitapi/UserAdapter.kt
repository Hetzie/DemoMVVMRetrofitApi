package com.excellentwebworld.demomvvmretrofitapi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.excellentwebworld.demomvvmretrofitapi.databinding.CustomListBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var userList = mutableListOf<UserDetails>()

    inner class ViewHolder(private val binding: CustomListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.model = userList[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CustomListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(user: List<UserDetails>){
        this.userList = user.toMutableList()
        notifyDataSetChanged()
    }
}