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
        private var okHttpClient = OkHttpClient.Builder().connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS).writeTimeout(180, TimeUnit.SECONDS).build()

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
        const val API_ENDPOINT = "http://192.168.43.246:5000/"
//        const val API_ENDPOINT = "http://192.168.41.95:5000/"
//        https://hair-cutz-backend.herokuapp.com/
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

        fun getName(context: Context): String {
            val pref = context.getSharedPreferences("NAME", MODE_PRIVATE)
            val token = pref?.getString("NAME", "JhonDoe")
            return token!!
        }

        fun setName(context: Context, name: String) {
            val pref = context.getSharedPreferences("NAME", MODE_PRIVATE)
            pref.edit().apply {
                putString("NAME", name)
                apply()
            }
        }

        fun clearName(context: Context) {
            val pref = context.getSharedPreferences("NAME", MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun getId(context: Context): Int {
            val pref = context.getSharedPreferences("ID", MODE_PRIVATE)
            val id = pref?.getInt("ID", 0)
            return id!!
        }

        fun setId(context: Context, id: Int) {
            val pref = context.getSharedPreferences("ID", MODE_PRIVATE)
            pref.edit().apply {
                putInt("ID", id)
                apply()
            }
        }

        fun clearId(context: Context) {
            val pref = context.getSharedPreferences("ID", MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun getGender(context: Context): String {
            val pref = context.getSharedPreferences("GENDER", MODE_PRIVATE)
            val token = pref?.getString("GENDER", "male")
            return token!!
        }

        fun setGender(context: Context, gender: String) {
            val pref = context.getSharedPreferences("GENDER", MODE_PRIVATE)
            pref.edit().apply {
                putString("GENDER", gender)
                apply()
            }
        }

        fun clearGender(context: Context) {
            val pref = context.getSharedPreferences("GENDER", MODE_PRIVATE)
            pref.edit().clear().apply()
        }

    }
}