package com.plugin.bigproject.models

import com.google.gson.annotations.SerializedName

data class Partners(
    @SerializedName("alamat") var alamat : String? = null,
    @SerializedName("id") var id : Int? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("lat") var latitude : Double? = null,
    @SerializedName("long") var longtitude : Double? = null,
    @SerializedName("nama_mitra") var namaMitra : String? = null
)
