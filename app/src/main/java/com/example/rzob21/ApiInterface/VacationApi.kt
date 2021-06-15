package com.example.rzob21.ApiInterface

import android.util.Log
import com.example.rzob21.models.PeriodModel
import com.example.rzob21.utilits.*
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.sql.Date

class VacationApi {

    fun getAllByYearAndMonth(date: Date){

        val request = Request.Builder().url("$BASE_URL/api/${UID}/vacation/$date").build()

        val client = OkHttpClient()

        //синхронный вызов
        client.newCall(request).execute().use { response ->
            response.use {
                try {
                    LIST_VACATION_OF_MONTH = mutableListOf()
                    val responseData = response.body?.string()
                    var json = Gson().fromJson(responseData, Array<PeriodModel>::class.java)
//                        if (json.isNotEmpty()){
                    for (i in json.indices){
                        LIST_VACATION_OF_MONTH.add(json[i])
                        Log.d("kek2", LIST_VACATION_OF_MONTH[i].toString())
                    }
//                        }
                }catch (e: Exception){
                    Log.d("kek2", e.toString())
                }


            }
        }
    }

    fun getAllByYear(date: Date){

        val request = Request.Builder().url("$BASE_URL/api/${UID}/vacation/year/$date").build()

        val client = OkHttpClient()

        //синхронный вызов
        client.newCall(request).execute().use { response ->
            response.use {
                try {
                    LIST_VACATION_OF_YEAR = mutableListOf()
                    val responseData = response.body?.string()
                    var json = Gson().fromJson(responseData, Array<PeriodModel>::class.java)
//                        if (json.isNotEmpty()){
                    for (i in json.indices){
                        LIST_VACATION_OF_YEAR.add(json[i])
                        Log.d("kek2", LIST_VACATION_OF_YEAR[i].toString())
                    }
//                        }
                }catch (e: Exception){
                    Log.d("kek2", e.toString())
                }


            }
        }
    }

    fun post(date_stop: String){
        //Это Post
        val jsonObject = JSONObject("""{"date_start":"$APP_DATE","date_stop":"$date_stop","user":{"id":"${USER.id}"}}""")
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        Log.d("kek", jsonObjectString)
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url("$BASE_URL/api/vacation").post(requestBody).build()

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
                    showToast("Отпуск добавлен!")
                }
            }

        })
    }

    fun put(vacation: PeriodModel){
        //        Это Put
        val id_sickleave = vacation.id
        val jsonObject = JSONObject("""{"date_start":"${vacation.date_start}","date_stop":"${vacation.date_stop}","user":{"id":"${USER.id}"}}""")
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        Log.d("kek", jsonObjectString)
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url("$BASE_URL/api/vacation/$id_sickleave").put(requestBody).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("kek", e.toString())
                APP_ACTIVITY.runOnUiThread {
                    showToast("Ошибка: $e")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.code.toString()
                Log.d("kek", body)
                APP_ACTIVITY.runOnUiThread {
                    showToast("Отпуск обновлен!")
                }
            }

        })
    }

    fun delete(vacationId: Int){
        //Это Delete

        val request = Request.Builder().url("$BASE_URL/api/vacation/${vacationId}").delete().build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("kek", e.toString())
                APP_ACTIVITY.runOnUiThread {
                    showToast("Ошибка: $e")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.code.toString()
                Log.d("kek", body)
                APP_ACTIVITY.runOnUiThread {
                    showToast("Отпуск удален!")
                }
            }

        })
    }
}