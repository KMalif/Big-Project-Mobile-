package com.plugin.bigproject.contracts

import com.plugin.bigproject.models.Recomendation
import okhttp3.MultipartBody
import okhttp3.RequestBody


interface CameraActivityContract {
    interface View{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getRecomendation(recomendations : List<Recomendation>, faceShape : String, suggest : String)
        fun showEmpty()
    }

    interface Presenter{
        fun prediction(token : String, image : MultipartBody.Part, hair : RequestBody, gender : RequestBody)
        fun destroy()
    }
}