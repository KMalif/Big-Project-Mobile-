package com.plugin.bigproject.models

import com.google.gson.annotations.SerializedName

data class Recomendation(
    @SerializedName("id") var id : String? = null,
    @SerializedName("bentuk") var shape : String? = null,
    @SerializedName("image") var image : String? = null,
)