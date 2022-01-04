package com.plugin.bigproject.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    var email : String? = null,
    var gender : String? = null,
    var id_user : Int? = null,
    var nama_user : String? = null,
    var no_hp : String? = null,
    var password : String?= null,
    var role : String? = null,
    var username : String? = null
): Parcelable

