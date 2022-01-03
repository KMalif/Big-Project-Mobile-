package com.plugin.bigproject.responses

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    @SerializedName("data") var data : T,
    @SerializedName("msg") var message : String,
    @SerializedName("status") var status : Int

)

data class WrappedListResponse<T>(
    @SerializedName("data") var data : List<T>,
    @SerializedName("msg") var message : String,
    @SerializedName("status") var status : Int

)

data class WrapperRecomendationResponse<T>(
    @SerializedName("Bentuk wajah") var shape : String,
    @SerializedName("data") var data : List<T>,
    @SerializedName("msg") var message : String,
    @SerializedName("status") var status : Int
)

data class ChatbotResponse(
    @SerializedName("answers") var answers : String,
    @SerializedName("msg") var msg : String,
    @SerializedName("response") var response : String,
    @SerializedName("status") var status : Int
)

data class PreviousChatResponse<T>(
    @SerializedName("data") var data : MutableList<T>,
    @SerializedName("msg") var message : String,
    @SerializedName("status") var status : Int
)