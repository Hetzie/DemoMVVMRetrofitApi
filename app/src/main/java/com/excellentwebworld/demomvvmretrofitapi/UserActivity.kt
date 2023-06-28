package com.excellentwebworld.demomvvmretrofitapi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.excellentwebworld.demomvvmretrofitapi.databinding.ActivityUserBinding
import com.excellentwebworld.demomvvmretrofitapi.databinding.OtherMethodaDialogBinding
import com.excellentwebworld.demomvvmretrofitapi.databinding.SearchUserDialogBinding

class UserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserBinding
    lateinit var viewModel: MainViewModel
    lateinit var userAdapter: UserAdapter
    var retrofitPlaceholderService = RetrofitService.getPlaceholderInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            MyViewModelFactory(MainRepository(retrofitPlaceholderService)))[MainViewModel::class.java]
        userAdapter = UserAdapter()
        viewModel.getAllUsers()

        viewModel.userLive.observe(this, Observer {
            userAdapter.setUsers(it)
        })


        viewModel.loading.observe(this, Observer {
            when (it) {
                true -> binding.pbUser.visibility = View.VISIBLE
                false -> binding.pbUser.visibility = View.GONE
            }
        })
        binding.rvUser.adapter = userAdapter
        binding.toolbarUserMain.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.btn_post -> {
                    intent(OtherMethodUserActivity::class.java, "method", "post")
                    true
                }
                R.id.btn_put -> {
                    intent(OtherMethodUserActivity::class.java, "method", "put")
                    true
                }
                R.id.btn_delete -> {
                    intent(OtherMethodUserActivity::class.java, "method", "delete")
                    true
                }
                R.id.btn_users -> {
                    intent(MainActivity::class.java)
                    true
                }
                R.id.btn_search -> {
                    searchUserByName()
                    true
                }
                else -> {
                    showToast("Something went Wrong")
                    false
                }
            }
        }
    }


    private fun searchUserByName() {
        val dialogView = SearchUserDialogBinding.inflate(layoutInflater)
        val etId = dialogView.etUserId.text
        showKeyBoard(dialogView.root)
        /*dialog("Find User", "by id", "Search", "Cancel",
            {
                var id = Integer.parseInt(etId.toString())
                if (id in 1..11){
                    viewModel.getUserById(id)

                }
                else{
                    showToast("Please Enter a valid id")
                }
                hideKeyBoard(dialogView.root)


            }, { hideKeyBoard(dialogView.root) }
            , dialogView.root)*/

        dialog("Find User", "by name", "Search", "Cancel",
            {
                val name = etId.toString()
                viewModel.getParticularUsers(name)
                hideKeyBoard(dialogView.root)


            }, { hideKeyBoard(dialogView.root) }, dialogView.root)
    }



}