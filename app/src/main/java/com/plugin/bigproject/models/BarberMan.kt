package com.plugin.bigproject.models

import com.google.gson.annotations.SerializedName

data class BarberMan(
    @SerializedName("id") var id : String? = null,
    @SerializedName("nama_karyawan") var name : String? = null,
    @SerializedName("mitra_id") var mitraId : String? = null,
    @SerializedName("nama_mitra") var partnerName : String? = null
)
