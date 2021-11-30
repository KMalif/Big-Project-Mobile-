package com.plugin.bigproject.models

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id") var id : String? = null,
    @SerializedName("title") var title : String? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("konten") var content : String? = null
)
