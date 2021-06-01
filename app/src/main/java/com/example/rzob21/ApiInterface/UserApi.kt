package com.example.rzob21.ApiInterface

import android.util.Log
import com.example.rzob21.models.User
import com.example.rzob21.utilits.*
import com.gefro.springbootkotlinRZOBbackend.models.Recast
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.sql.Date

class UserApi() {

    fun checkUser(uid: String, email: String) {

        Log.d("user", UID)
        val request = Request.Builder().url("$BASE_URL/api/user/$uid").build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.code != 200){
                    UserApi().post(uid, email)
                }
            }
        })
    }

    fun post(uid: String, phone: String){
        //Это Post
        val jsonObject = JSONObject("""{"id":"$uid","phone":"$phone"}""")
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
//        Log.d("kek", jsonObjectString)
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url("$BASE_URL/api/user").post(requestBody).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                APP_ACTIVITY.runOnUiThread {
                    showToast("Ошибка: $e")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.code.toString()
                Log.d("kek", body)
                APP_ACTIVITY.runOnUiThread {
                    showToast("Аккаунт добавлен на основной сервер!")
                }
            }

        })
    }

    fun put() {
        //Это Put

        val jsonObject = JSONObject("""{"id":"${USER.id}","phone":"${USER.phone}","salary":"${USER.salary}","position":"${USER.position}", "average_salary":"${USER.average_salary}"}""")
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
//        Log.d("kek", jsonObjectString)
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url("$BASE_URL/api/user/${USER.id}").put(requestBody).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                APP_ACTIVITY.runOnUiThread {
                    showToast("Ошибка: $e")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.code.toString()
                Log.d("kek", body)
                APP_ACTIVITY.runOnUiThread {
                    showToast("Информация об аккаунте обновлена!")
                }
            }

        })
    }
}