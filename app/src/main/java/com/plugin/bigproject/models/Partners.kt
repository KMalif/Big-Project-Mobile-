package com.plugin.bigproject.models

import com.google.gson.annotations.SerializedName

data class Partners(
    @SerializedName("id") var id : String? = null,
    @SerializedName("nama_mitra") var namaMitra : String? = null,
    @SerializedName("lokasi") var lokasi : String? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("Lat") var latitude : String? = null,
    @SerializedName("Long") var longtitude : String? = null
)
