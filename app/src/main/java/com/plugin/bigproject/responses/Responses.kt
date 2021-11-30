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