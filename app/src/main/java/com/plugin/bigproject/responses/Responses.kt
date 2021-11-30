package com.plugin.bigproject.responses

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    @SerializedName("msg") var message : String,
    @SerializedName("status") var status : Int,
    @SerializedName("data") var data : T
)

data class WrappedListResponse<T>(
    @SerializedName("msg") var message : String,
    @SerializedName("status") var status : Int,
    @SerializedName("data") var data : List<T>
)

data class WrapperRecomendationResponse<T>(
    @SerializedName("msg") var message : String,
    @SerializedName("status") var status : Int,
    @SerializedName("Bentuk wajah") var shape : String,
    @SerializedName("Rekomendasi") var recomendation : List<T>
)