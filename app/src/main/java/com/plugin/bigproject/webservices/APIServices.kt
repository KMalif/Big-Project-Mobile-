package com.plugin.bigproject.webservices

import com.plugin.bigproject.models.*
import com.plugin.bigproject.responses.WrappedListResponse
import com.plugin.bigproject.responses.WrappedResponse
import retrofit2.Call
import retrofit2.http.*

interface APIServices {

    //Sign in
    @FormUrlEncoded
    @POST("signin")
    fun login (
        @Field("email") email : String,
        @Field("password") password : String
    ):Call<WrappedResponse<User>>

    //Sign up
    @FormUrlEncoded
    @POST("signup")
    fun register (
        @Field("name") name : String,
        @Field("username") username : String,
        @Field("email") email : String,
        @Field("password") password : String
    ):Call<WrappedResponse<User>>

    //Edit profile
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

    //get Haircuts
    @GET("haircuts")
    fun getHaircuts(

    ): Call<WrappedListResponse<HairCuts>>

    //get Partners
    @GET("partners")
    fun getPartners(

    ): Call<WrappedListResponse<Partners>>

    //get barberman
    @GET("barberman/{id}")
    fun getbarberMan(
        @Path("id") id: String
    ): Call<WrappedListResponse<BarberMan>>

    //get news
    @GET("news")
    fun getNews(

    ):Call<WrappedListResponse<News>>




}