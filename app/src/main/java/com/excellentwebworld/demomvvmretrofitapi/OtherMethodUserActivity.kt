package com.excellentwebworld.demomvvmretrofitapi

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.excellentwebworld.demomvvmretrofitapi.databinding.ActivityPostUserBinding

class OtherMethodUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityPostUserBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getServiceInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPostUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            MyViewModelFactory(MainRepository(retrofitService)))[MainViewModel::class.java]


        when (intent.getStringExtra("method")) {
            "post" -> {
                binding.apply {
                    btnPostUser.visibility = View.VISIBLE
                    llPost.visibility = View.VISIBLE
                    postServiceModel = ServiceLoginResponse("", ServiceData("", "", ""))
                }
                binding.btnPostUser.setOnClickListener {
                    val phone = binding.methodUserName.text.toString()
                    val deviceToken = binding.methodUserJob.text.toString()
                    viewModel.createUserMultipart(ServiceLoginRequest(phone, "note", "android", deviceToken))
                    binding.methodUserName.text.clear()
                    binding.methodUserJob.text.clear()
                }

                viewModel.loading.observe(this, Observer {
                    when (it) {
                        true -> {
                            binding.pbPost.visibility = View.VISIBLE
                            binding.postServiceModel = ServiceLoginResponse("", ServiceData("", "", ""))
                        }
                        false -> {
                            binding.pbPost.visibility = View.GONE
                            binding.postServiceModel = viewModel.postServiceLive.value
                        }
                    }
                })


            }
            "put" -> {
                binding.apply {
                    btnUpdateUser.visibility = View.VISIBLE
                    llPut.visibility = View.VISIBLE
                    putModel = UserResponseOnUpdate("", "", "")
                }
                binding.btnUpdateUser.setOnClickListener {
                    val name = binding.methodUserName.text.toString()
                    val job = binding.methodUserJob.text.toString()
                    viewModel.updateUser(UserRequest(name, job), 6)
                    binding.putModel = viewModel.putUserLive.value
                    binding.methodUserName.text.clear()
                    binding.methodUserJob.text.clear()
                }
                viewModel.loading.observe(this, Observer {
                    when (it) {
                        true -> {
                            binding.pbPost.visibility = View.VISIBLE
                            binding.putModel = UserResponseOnUpdate("", "", "")
                        }
                        false -> {
                            binding.pbPost.visibility = View.GONE
                            binding.putModel = viewModel.putUserLive.value
                        }
                    }
                })


            }
            "delete" -> {
                binding.apply {
                    methodUserId.visibility = View.VISIBLE
                    methodUserName.visibility = View.GONE
                    methodUserJob.visibility = View.GONE
                    btnDeleteUser.visibility = View.VISIBLE
                }
                binding.btnDeleteUser.setOnClickListener {
                    val id = binding.methodUserId.text.toString()
                    viewModel.deleteUser(this, Integer.parseInt(id))
                    binding.methodUserId.text.clear()
                }
                viewModel.loading.observe(this, Observer {
                    when (it) {
                        true -> {
                            binding.pbPost.visibility = View.VISIBLE
                        }
                        false -> {
                            binding.pbPost.visibility = View.GONE
                        }
                    }
                })

            }
        }
    }
}