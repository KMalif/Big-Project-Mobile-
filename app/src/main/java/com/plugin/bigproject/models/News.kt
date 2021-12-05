package com.plugin.bigproject.models

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("content") var content : String? = null,
    @SerializedName("id") var id : Int? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("title") var title : String? = null


)
