package com.plugin.bigproject.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class User(
    @SerializedName("id") var id : String? = null
)
