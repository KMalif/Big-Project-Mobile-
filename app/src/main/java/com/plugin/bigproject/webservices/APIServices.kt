package com.plugin.bigproject.webservices

import com.plugin.bigproject.models.BarberMan
import com.plugin.bigproject.models.HairCuts
import com.plugin.bigproject.models.Partners
import com.plugin.bigproject.models.User
import com.plugin.bigproject.responses.WrappedListResponse
import com.plugin.bigproject.responses.WrappedResponse
import retrofit2.Call
import retrofit2.http.*

interface APIServices {


    @FormUrlEncoded
    @POST("signin")
    fun login (
        @Field("email") email : String,
        @Field("password") password : String
    ):Call<WrappedResponse<User>>

    @FormUrlEncoded
    @POST("signup")
    fun register (
        @Field("name") name : String,
        @Field("username") username : String,
        @Field("email") email : String,
        @Field("password") password : String
    ):Call<WrappedResponse<User>>

    @FormUrlEncoded
    @PUT("edit-profile/{id}")
    fun editProfile(
        @Header("Authorization") api_token: String,
        @Path("id")id : String,
        @Field("name")  name : String,
        @Field("email")  email: String,
        @Field("password")  password : String
    ): Call<WrappedResponse<User>>

    @FormUrlEncoded
    @PUT("edit-profile/{id}")
    fun editProfileWithoutPassword(
        @Header("Authorization") api_token: String,
        @Path("id")id : String,
        @Field("name")  name : String,
        @Field("email")  email: String,
    ): Call<WrappedResponse<User>>

    @GET("haircuts")
    fun getHaircuts(

    ): Call<WrappedListResponse<HairCuts>>

    @GET("partners")
    fun getPartners(

    ): Call<WrappedListResponse<Partners>>

    @GET("barberman/{id}")
    fun getbarberMan(
        @Path("id") id: String
    ): Call<WrappedListResponse<BarberMan>>





}