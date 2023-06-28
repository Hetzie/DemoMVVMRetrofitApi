package com.excellentwebworld.demomvvmretrofitapi

import com.google.gson.annotations.SerializedName

data class UserRequest(
    var name: String,
    var job: String,
)

data class UserResponse(
    var name: String,
    var job: String,
    var id: String,
    var createdAt: String,
)

data class ServiceLoginRequest(
    var phone: String,
    @SerializedName("device_name")
    var deviceName: String,
    @SerializedName("device_type")
    var deviceType: String,
    @SerializedName("device_token")
    var deviceToken: String,

)

data class ServiceLoginResponse(
    var message: String,
    var data: ServiceData
)
data class ServiceData(
    var name: String,
    var email: String,
    var phone: String,
)

data class UserResponseOnUpdate(
    var name: String,
    var job: String,
    var updatedAt: String,
)

data class UserDetails(
    var id: Int,
    var name: String,
    var username: String,
    var email: String,
)

data class loginUser(
    val email: String,
    val password: String
)
