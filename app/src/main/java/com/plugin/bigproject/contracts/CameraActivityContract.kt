package com.plugin.bigproject.contracts

import okhttp3.MultipartBody


interface CameraActivityContract {
    interface View{
        fun showToast(message : String)
    }

    interface Presenter{
        fun prediction(image : MultipartBody.Part, hair : String)
        fun destroy()
    }
}