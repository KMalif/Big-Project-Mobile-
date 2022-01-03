package com.plugin.bigproject.models

import android.os.Parcelable

data class User(
    var id : Int? = null,
    var name : String? = null,
    var email : String? = null,
    var role : String? = null,
    var username : String? = null,
    var token : String? = null,
    var refresh_token : String? = null
)
