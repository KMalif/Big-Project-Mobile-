package com.plugin.bigproject.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

fun ContentResolver.getFileName(uri : Uri) : String{

    var name = ""
    val cursor = query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}

//sk.eyJ1Ijoia21hbGlmIiwiYSI6ImNreHNydDQ3eDE2M3YydW12bm45MnBvcTIifQ.kzkK34IprgBKOrk8_LnvZg