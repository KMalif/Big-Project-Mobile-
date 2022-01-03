package com.plugin.bigproject.webservices

import com.plugin.bigproject.models.*
import com.plugin.bigproject.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIServices {

    //Sign in
    @FormUrlEncoded
    @POST("signin/")
    fun login (
        @Field("username") username : String,
        @Field("password") password : String
    ):Call<WrappedResponse<User>>

    //Sign up
    @FormUrlEncoded
    @POST("signup/")
    fun register (
        @Field("name") name : String,
        @Field("username") username : String,
        @Field("email") email : String,
        @Field("password") password : String
    ):Call<WrappedResponse<User>>

    @GET("user/{id}")
    fun getUserById(
        @Path("id") id : Int
    ): Call<WrappedResponse<User>>

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
    @GET("mitra/")
    fun getPartners(
        @Header("Authorization") api_token: String
    ): Call<WrappedListResponse<Partners>>

    //get Partners
    @GET("mitra/{id}")
    fun getPartnerbyId(
        @Path("id") id: Int
    ): Call<WrappedResponse<Partners>>

    //get barberman
    @GET("barberman/{id}")
    fun getbarberMan(
        @Path("id") id: String
    ): Call<WrappedListResponse<BarberMan>>

    //get news
    @GET("berita/")
    fun getNews(

    ):Call<WrappedListResponse<News>>

    //GEt newsbyID
    @GET("berita/{id}")
    fun getNewsByID(
        @Path("id") id : Int
    ):Call<WrappedResponse<News>>


    @Multipart
    @POST("predict/")
    fun predict(
        @Part files : MultipartBody.Part,
        @Part("panjang") panjang : RequestBody
    ):Call<WrapperRecomendationResponse<Recomendation>>

    @FormUrlEncoded
    @POST("chatbot")
    fun sendChat(
        @Header("Authorization") api_token: String,
        @Field("chat") chat : String
    ):Call<ChatbotResponse>

    @GET("chatbot")
    fun getPreviousChat(
        @Header("Authorization") api_token: String
    ):Call<PreviousChatResponse<Message>>

}