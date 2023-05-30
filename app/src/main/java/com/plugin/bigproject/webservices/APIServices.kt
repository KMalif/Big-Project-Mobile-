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
    @POST("signin")
    fun login (
        @Field("username") username : String,
        @Field("password") password : String
    ):Call<WrappedResponse<User>>

    //Sign up
    @FormUrlEncoded
    @POST("signup")
    fun register (
        @Field("nama_user") name : String,
        @Field("username") username : String,
        @Field("gender") gender : String,
        @Field("no_hp") no_hp : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("role") role : String
    ):Call<WrappedResponse<User>>

    @GET("user")
    fun getUserById(
        @Header("Authorization") api_token: String
    ): Call<WrappedResponse<Profile>>

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
    @GET("hairmodel")
    fun getHaircuts(
        @Header("Authorization") api_token: String
    ): Call<WrappedListResponse<HairCuts>>

    //get Partners
    @GET("mitra")
    fun getPartners(
        @Header("Authorization") api_token: String
    ): Call<WrappedListResponse<Partners>>

    //get Partners
    @GET("mitra/{id}")
    fun getPartnerbyId(
        @Header("Authorization") api_token: String,
        @Path("id") id: Int
    ): Call<WrappedResponse<Partners>>

    //get barberman
    @GET("barberman/{id}")
    fun getbarberMan(
        @Path("id") id: String
    ): Call<WrappedListResponse<BarberMan>>

    //get news
    @GET("news")
    fun getNews(
        @Header("Authorization") api_token: String
    ):Call<WrappedListResponse<News>>

    //GEt newsbyID
    @GET("news/{id}")
    fun getNewsByID(
        @Path("id") id : Int,
        @Header("Authorization") api_token: String
    ):Call<WrappedResponse<News>>


    @Multipart
    @POST("predict")
    fun predict(
        @Header("Authorization") api_token: String,
        @Part files : MultipartBody.Part,
        @Part("panjang") panjang : RequestBody,
        @Part("gender") gender : RequestBody
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

    @FormUrlEncoded
    @POST("bookUser")
    fun booking(
        @Header("Authorization") api_token: String,
        @Field("id_mitra") idMitra : Int,
        @Field("status") status : String
    ): Call<WrappedResponse<Booking>>

    @GET("bookUser")
    fun getBookingHistory(
        @Header("Authorization") api_token: String
    ): Call<WrappedListResponse<BookHistory>>

    @GET("antrian/{id}")
    fun getAntre(
        @Header("Authorization") api_token: String,
        @Path("id") id_mitra : Int

    ):Call<WrappedListResponse<Antre>>

    //get history
    @GET("whistlist")
    fun getHistory(
        @Header("Authorization") api_token: String
    ):Call<WrappedListResponse<History>>

    @POST("whistlist")
    fun addWishlist(
        @Header("Authorization") api_token: String,
        @Body requestBody : RequestBody
    ):Call<WrappedResponse<History>>
}