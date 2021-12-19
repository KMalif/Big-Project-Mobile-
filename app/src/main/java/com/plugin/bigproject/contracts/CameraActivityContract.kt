package com.plugin.bigproject.contracts

import okhttp3.MultipartBody
import okhttp3.RequestBody


interface CameraActivityContract {
    interface View{
        fun showToast(message : String)
    }

    interface Presenter{
        fun prediction(image : MultipartBody.Part, hair : RequestBody)
        fun destroy()
    }
}