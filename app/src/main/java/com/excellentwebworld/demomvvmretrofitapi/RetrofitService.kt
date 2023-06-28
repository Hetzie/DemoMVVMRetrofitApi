package com.excellentwebworld.demomvvmretrofitapi

import android.util.Log
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface RetrofitService {

    @GET("users")
    fun getUsers(): Call<List<UserDetails>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<UserDetails>

    @GET("entries")
    fun getEntries(): Call<Entry>

    @GET("entries")
    fun getParticularEntries(api: String): Call<EntryDetails>

    @POST("api/users")
    fun createUser(@Body userRequest: UserRequest): Call<UserResponse>

    @FormUrlEncoded
    @POST("api/users")
    fun createUserFormUrlEncoded(
        @Field("name") Name: String,
        @Field("job") job: String,
    ): Call<UserResponse>

    /*@Multipart
    @POST("api/users")
    fun createUserMultipart(
        @Part name: MultipartBody.Part,
        @Part job: MultipartBody.Part,
    ): Call<UserResponse>*/

    @Multipart
    @POST("api/login")
    fun createUserMultipart(
        @Part("phone") phone: RequestBody,
        @Part("device_name") deviceName: RequestBody,
        @Part("device_type") deviceType: RequestBody,
        @Part("device_token") deviceToken: RequestBody,
    ): Call<ServiceLoginResponse>


    @PUT("api/users/{id}")
    fun updateUser(@Body userRequest: UserRequest, @Path("id") id:Int): Call<UserResponseOnUpdate>

    @DELETE("api/users/{id}")
    fun deleteUser(@Path("id") id:Int):Call<String>

    @POST("entries")
    fun createEntry(@Body entryDetails: EntryDetails): Call<EntryDetails>


    companion object  {
        private const val baseURL = "https://reqres.in/"
        private const val baseUrlPlaceholder = "https://jsonplaceholder.typicode.com/"
        private const val baseUrlPublicApis = "https://api.publicapis.org/"
        private const val baseUrlServices = "https://services.excellentwebworld.in/"

        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            val retrofit = Retrofit
                .Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)
            Log.i("RetrofitHelper", "Here")

            return retrofitService!!
        }

        fun getServiceInstance():RetrofitService{
            val retrofit = Retrofit
                .Builder()
                .baseUrl(baseUrlServices)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)
            return retrofitService!!
        }

        fun getInstanceOfPublicApis(): RetrofitService {
            val retrofit = Retrofit
                .Builder()
                .baseUrl(baseUrlPublicApis)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)
            Log.i("RetrofitHelper", "Here")

            return retrofitService!!
        }

        fun getPlaceholderInstance(): RetrofitService {
            val retrofit = Retrofit
                .Builder()
                .baseUrl(baseUrlPlaceholder)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)
            return retrofitService!!
        }
    }
}