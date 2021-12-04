package com.plugin.bigproject.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.plugin.bigproject.webservices.APIServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    companion object{
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()

        fun APIService():APIServices = getClient().create(APIServices::class.java)

        private fun getClient():Retrofit{
            return if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(Constants.API_ENDPOINT)
                    .client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            }else{
                retrofit!!
            }
        }
    }
}

class Constants{
    companion object{
        const val API_ENDPOINT = "https://hair-cutz.herokuapp.com/"

        fun getToken(context: Context): String {
            val pref = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            val token = pref?.getString("TOKEN", "UNDEFINED")
            return token!!
        }

        fun setToken(context: Context, token: String) {
            val pref = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                apply()
            }
        }

        fun clearToken(context: Context) {
            val pref = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            pref.edit().clear().apply()
        }
    }
}