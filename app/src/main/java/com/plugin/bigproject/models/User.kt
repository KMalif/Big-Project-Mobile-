package com.plugin.bigproject.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class User(
    @SerializedName("id") var id : String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("email") var email : String? = null,
    @SerializedName("role") var role : String? = null,
    @SerializedName("username") var username : String? = null,
    @SerializedName("token_access") var tokenAccess : String? = null,
    @SerializedName("token_refresh") var tokenRefresh : String? = null
)
