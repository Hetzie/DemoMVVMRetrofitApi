package com.excellentwebworld.demomvvmretrofitapi

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainViewModel(var repository: MainRepository) : ViewModel() {
    var userLive = MutableLiveData<List<UserDetails>>()
    var userList = mutableListOf<UserDetails>()
    var entry = MutableLiveData<List<EntryDetails>>()
    val entryUpdatedList = mutableListOf<EntryDetails>()
    var postEntry = MutableLiveData<EntryDetails>()
    var errorMessage = MutableLiveData<String>()
    var loading = MutableLiveData<Boolean>()
    var postUserLive = MutableLiveData<UserResponse>()
    var putUserLive = MutableLiveData<UserResponseOnUpdate>()
    val postServiceLive = MutableLiveData<ServiceLoginResponse>()


    fun getAllUsers() {
        loading.postValue(true)
        val response = repository.getAllUsers()
        response.enqueue(object : Callback<List<UserDetails>> {
            override fun onResponse(
                call: Call<List<UserDetails>>,
                response: Response<List<UserDetails>>,
            ) {
                userLive.value = response.body()!!
                loading.postValue(false)
            }

            override fun onFailure(call: Call<List<UserDetails>>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.postValue(false)
            }

        })
    }

    fun getParticularUsers(name: String) {
        val response = repository.getAllUsers()
        response.enqueue(object : Callback<List<UserDetails>> {
            override fun onResponse(
                call: Call<List<UserDetails>>,
                response: Response<List<UserDetails>>,
            ) {
                for (i in response.body()!!) {
                    if (i.name.contains(name)) {
                        userList.add(i)
                    }
                }
                userLive.value = userList
                userList = mutableListOf()
                loading.postValue(false)
            }

            override fun onFailure(call: Call<List<UserDetails>>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.postValue(false)
            }

        })

    }

    fun getUserById(id: Int) {
        val response = repository.getUserById(id)
        response.enqueue(object : Callback<UserDetails> {
            override fun onResponse(
                call: Call<UserDetails>,
                response: Response<UserDetails>,
            ) {
                Log.e("onResponse", response.body().toString())
            }

            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                Log.e("onResponse", t.message.toString())
            }

        })
    }

    fun createUser(userRequest: UserRequest) {
        loading.postValue(true)
        val response = repository.createUser(userRequest)
        response.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                postUserLive.value = response.body()
                Log.e("createUser", response.body().toString())
                loading.postValue(false)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.postValue(false)
            }
        })
    }

    fun updateUser(userRequest: UserRequest, id: Int) {
        loading.postValue(true)
        val response = repository.updateUser(userRequest, id)
        response.enqueue(object : Callback<UserResponseOnUpdate> {
            override fun onResponse(
                call: Call<UserResponseOnUpdate>,
                response: Response<UserResponseOnUpdate>,
            ) {
                putUserLive.value = response.body()
                Log.e("updateUser", response.body().toString())
                loading.postValue(false)
            }

            override fun onFailure(call: Call<UserResponseOnUpdate>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.postValue(false)
            }

        })
    }

    fun deleteUser(context: Context, id: Int) {
        loading.postValue(true)
        val response = repository.deleteUser(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code() == 204) {
                    context.showToast("User Deleted Successfully")
                }
                Log.e("deleteUser", response.code().toString())
                loading.postValue(false)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                errorMessage.postValue(t.message)
                context.showToast("User Deleted Successfully")
                loading.postValue(false)
            }

        })
    }

    fun crateUserFormUrlEncoded(userRequest: UserRequest) {
        loading.postValue(true)
        val response = repository.createUserFormUrlEncoded(userRequest)
        response.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                postUserLive.value = response.body()
                loading.postValue(false)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.postValue(false)
            }
        })
    }

    fun createUserMultipart(serviceLoginRequest: ServiceLoginRequest) {
        loading.postValue(true)

//        val name = MultipartBody.Part.createFormData("name", userRequest.name)
//        val job = MultipartBody.Part.createFormData("job", userRequest.job)

        val phone = RequestBody.create(MediaType.parse("text/plain"), serviceLoginRequest.phone)
        val deviceName = RequestBody.create(MediaType.parse("text/plain"), serviceLoginRequest.deviceName)
        val deviceType = RequestBody.create(MediaType.parse("text/plain"), serviceLoginRequest.deviceType)
        val deviceToken = RequestBody.create(MediaType.parse("text/plain"), serviceLoginRequest.deviceToken)


        Log.e("createUserMultipart", "phone = ${bodyToString(phone)}")

        val response = repository.createUserMultipart(phone, deviceName, deviceType,deviceToken)
        response.enqueue(object : Callback<ServiceLoginResponse>{
            override fun onResponse(
                call: Call<ServiceLoginResponse>,
                response: Response<ServiceLoginResponse>,
            ) {
                postServiceLive.value = response.body()
                loading.postValue(false)
            }

            override fun onFailure(call: Call<ServiceLoginResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.postValue(false)
            }

        })

    }

    fun getAllEntries() {
        loading.postValue(true)
        val response = repository.getAllEntries()
        response.enqueue(object : Callback<Entry> {
            override fun onResponse(call: Call<Entry>, response: Response<Entry>) {
                entryUpdatedList.addAll(response.body()!!.entries)
                entry.postValue(entryUpdatedList)
                loading.postValue(false)
            }

            override fun onFailure(call: Call<Entry>, t: Throwable) {
                errorMessage.postValue(t.message)
                loading.postValue(false)
            }

        })
    }

    fun getParticularEntry(api: String) {
        val response = repository.getParticularEntries(api)
        response.enqueue(object : Callback<EntryDetails> {
            override fun onResponse(call: Call<EntryDetails>, response: Response<EntryDetails>) {
                if (response.body()!!.API == api) {
                    Log.e("getParticularEntries", response.body().toString())
                } else {
                    Log.e("getParticularEntries", "Not Found")
                }
            }

            override fun onFailure(call: Call<EntryDetails>, t: Throwable) {
                Log.e("getParticularEntries", "onFailure")
            }

        })
    }

    fun postEntryFun(obj: EntryDetails) {
        repository.createEntry(obj).enqueue(object : Callback<EntryDetails> {
            override fun onResponse(call: Call<EntryDetails>, response: Response<EntryDetails>) {

                entryUpdatedList.add(obj)
                entry.postValue(entryUpdatedList)
                Log.e("onResponse", postEntry.value.toString())
            }

            override fun onFailure(call: Call<EntryDetails>, t: Throwable) {
                Log.e("postEntry", "onFailure")
            }
        })
    }

/*
    fun postUser(obj: UserDetails) {
        val response = repository.createUser(obj)
        response.enqueue(object : Callback<UserDetails>{
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                user.
            }

            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
*/
private fun bodyToString(request: RequestBody): String? {
    return try {
        val buffer = Buffer()
        request.writeTo(buffer)
        buffer.readUtf8()
    } catch (e: IOException) {
        "did not work"
    }
}
}