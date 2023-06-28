package com.excellentwebworld.demomvvmretrofitapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.excellentwebworld.demomvvmretrofitapi.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}