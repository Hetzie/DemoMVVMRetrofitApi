package com.excellentwebworld.demomvvmretrofitapi

import okhttp3.MultipartBody
import okhttp3.RequestBody

class MainRepository constructor(private val retrofitService: RetrofitService) {


    fun getAllUsers() = retrofitService.getUsers()
    fun getUserById(id: Int) = retrofitService.getUserById(id)

    fun createUser(userRequest: UserRequest) = retrofitService.createUser(userRequest)
    fun updateUser(userRequest: UserRequest, id: Int) = retrofitService.updateUser(userRequest, id)
    fun deleteUser(id: Int) = retrofitService.deleteUser(id)

    fun createUserFormUrlEncoded(userRequest: UserRequest) =
        retrofitService.createUserFormUrlEncoded(userRequest.name, userRequest.job)

/*
    fun createUserMultipart(namePart: MultipartBody.Part, jobPart: MultipartBody.Part) =
        retrofitService.createUserMultipart(namePart, jobPart)
*/

    fun createUserMultipart(phone: RequestBody, deviceName: RequestBody, deviceType: RequestBody, deviceToken: RequestBody) =
        retrofitService.createUserMultipart(phone, deviceName, deviceType,deviceToken)

    fun getAllEntries() = retrofitService.getEntries()
    fun getParticularEntries(api: String) = retrofitService.getParticularEntries(api)
    fun createEntry(obj: EntryDetails) = retrofitService.createEntry(obj)

}