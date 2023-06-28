package com.excellentwebworld.demomvvmretrofitapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.excellentwebworld.demomvvmretrofitapi.databinding.ActivityMainBinding
import com.excellentwebworld.demomvvmretrofitapi.databinding.AddUserDialogBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    lateinit var entryAdapter: EntryAdapter
    var retrofitPublicApiService = RetrofitService.getInstanceOfPublicApis()


    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this,
                MyViewModelFactory(MainRepository(retrofitPublicApiService)))[MainViewModel::class.java]

        entryAdapter = EntryAdapter()
//        viewModel.postEntryFun(EntryDetails("1", "1", "1", true, "1", "1", "1"))
        viewModel.getAllEntries()

        viewModel.entry.observe(this, Observer {
            entryAdapter.setUsers(it)
        })

        viewModel.loading.observe(this, Observer {
            when(it) {
                true -> binding.pbMain.visibility = View.VISIBLE
                false -> binding.pbMain.visibility = View.GONE
                }
            })

        binding.apply {
            viewModel = viewModel
            rvMain.adapter = entryAdapter
            binding.toolbarMain.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.btn_users -> {
                        intent(UserActivity::class.java)
                        true
                    }
                    else -> {
                        showToast("Something went Wrong")
                        false
                    }
                }
            }
        }
//            fabPostData.setOnClickListener { intent(AddUserActivity::class.java) }
            binding.fabPostData.setOnClickListener {
                val inflater = layoutInflater
                val dialogView = AddUserDialogBinding.inflate(inflater)
                val etApi = dialogView.etAddEntryApi.text
                val etDescription = dialogView.etAddEntryDescription.text
                val etAuth = dialogView.etAddEntryAuth.text
                val etCategory = dialogView.etAddEntryCategory.text

                dialog(
                    "Add User", "Enter following details to add User:", "Add", "Cancel",
                    {
                        val api = etApi.toString()
                        val description = etDescription.toString()
                        val auth = etAuth.toString()
                        val category = etCategory.toString()
                        /*viewModel.postEntryFun(EntryDetails(api,
                            description,
                            auth,
                            true,
                            "aaaaaaaaa",
                            "bbbbbbbbbb",
                            category))*/
                        viewModel.getParticularEntry(api)
                        Log.e("onResponse",viewModel.entry.value.toString())
                        viewModel.entry.observe(this@MainActivity, Observer {
                            entryAdapter.setUsers(it)
                        })
                    }, null, dialogView.root
                )
            }



        /*val userApi = RetrofitService.getInstance().create(RetrofitService::class.java)
        val postApi = RetrofitService.getPlaceholderInstance().create(RetrofitService::class.java)

        GlobalScope.launch (Dispatchers.Main){
            val user = userApi.getAllUsers()
            for (i in user.body()!!.users){
                Log.e("Name", i.name + i.email)
            }

            val posts = postApi.getPosts()
            for (i in 0 until posts.body()!!.size){
                Log.e("POST", posts.body()!![i].title)
            }
        }*/

    }




}