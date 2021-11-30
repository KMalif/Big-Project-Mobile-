package com.plugin.bigproject.models

import com.google.gson.annotations.SerializedName

data class HairCuts (
    @SerializedName("id") var id : String? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("name") var nama : String? = null,
    @SerializedName("deskripsi") var deskripsi : String? = null
)