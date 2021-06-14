package com.example.rzob21.ApiInterface

import android.util.Log
import android.widget.Toast
import com.example.rzob21.utilits.*
import com.gefro.springbootkotlinRZOBbackend.models.Recast
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.sql.Date


class RecastApi(){
//    fun getByUserAndDate(){
//        //Это getByUserAndDate
//        val user_id = "kek"
//        val date = "2021-04-17"
//        val request = Request.Builder().url("$BASE_URL/api/$user_id/recasts/$date").build()
//
//        val client = OkHttpClient()
//        client.newCall(request).enqueue(object: Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d("kek", e.toString())
//            }
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    if(response.code == 200) {
//                        val responseData = response.body?.string()
//                        var json = Gson().fromJson(responseData, Array<Recast>::class.java)
//                        Log.d("kek", json[0].toString())
//                    }
//                }
//            }
//        })
//    }

    fun getAllByYear(date: Date){
        //Это getAllByYear
//        var list = mutableListOf<Recast>()
//        val user_id = "kek"

        val request = Request.Builder().url("$BASE_URL/api/${USER.id}/recasts/calendarfragment/year/$date").build()

        val client = OkHttpClient()
        //асинхронный вызов
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("kek", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    try {
                        LIST_RECAST_OF_YEAR = mutableListOf()
                        val responseData = response.body?.string()
                        var json = Gson().fromJson(responseData, Array<Recast>::class.java)
//                        if (json.isNotEmpty()){
                            for (i in json.indices){
                                LIST_RECAST_OF_YEAR.add(Date.valueOf(json[i].date))
                            }
                        Log.d("kek20", LIST_RECAST_OF_YEAR.toString())
//                        }
                    }catch (e: Exception){
                        Log.d("kek20", e.toString())
                    }


                }

            }
        })
        //синхронный вызов
//        client.newCall(request).execute().use { response ->
//            response.use {
//                try {
//                    LIST_RECAST_OF_YEAR = mutableListOf()
//                    val responseData = response.body?.string()
//                    var json = Gson().fromJson(responseData, Array<Recast>::class.java)
////                        if (json.isNotEmpty()){
//                        for (i in json.indices){
//                            LIST_RECAST_OF_YEAR.add(json[i])
//                                Log.d("kek2", LIST_RECAST_OF_YEAR[i].toString())
//                        }
////                        }
//                }catch (e: Exception){
//                    Log.d("kek2", e.toString())
//                }
//
//
//            }
//        }
    }

    fun getAllByYearAndMonth(date: Date){

        Log.d("user", UID)
        val request = Request.Builder().url("$BASE_URL/api/${UID}/recasts/test/yearmonth/$date").build()

        val client = OkHttpClient()

        //синхронный вызов
        client.newCall(request).execute().use { response ->
            response.use {
                try {
                    LIST_RECAST_OF_MONTH = mutableListOf()
                    val responseData = response.body?.string()
                    var json = Gson().fromJson(responseData, Array<Recast>::class.java)
//                        if (json.isNotEmpty()){
                    for (i in json.indices){
                        LIST_RECAST_OF_MONTH.add(json[i])
                        Log.d("kek2", LIST_RECAST_OF_MONTH[i].toString())
                    }
                    LIST_RECAST_OF_MONTH.sortBy { it.date }
//                        }
                }catch (e: Exception){
                    Log.d("kek2", e.toString())
                }


            }
        }
    }

    fun getAll (){
                //Это getAll
        var list = mutableListOf<Recast>()
        //val user_id = "kek"

        val request = Request.Builder().url("$BASE_URL/api/${USER.id}/recasts/").build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("kek", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    val responseData = response.body?.string()
                    var json = Gson().fromJson(responseData, Array<Recast>::class.java)
                    for (i in json.indices) {
                        list.add(json[i])
                        Log.d("kek", list[i].toString())
                    }
                }
            }
        })
    }

    fun delete(recast: Recast){
        //Это Delete
        // val id_recast = 37

        val request = Request.Builder().url("$BASE_URL/api/recasts/${recast.id}").delete().build()

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
                    showToast("Переработка удалена!")
                }
            }

        })
    }

    fun put(recast: Recast){
        //        Это Put
        val id_recast = recast.id
        val jsonObject = JSONObject("""{"date":"${recast.date}","recasthours":"${recast.recasthours}","user":{"id":"${USER.id}"}}""")
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        Log.d("kek", jsonObjectString)
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url("$BASE_URL/api/recasts/$id_recast").put(requestBody).build()

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
                    showToast("Переработка обновлена!")
                }
            }

        })
    }

    fun post(recast_hours: Double){
        //Это Post
        val jsonObject = JSONObject("""{"date":"$APP_DATE","recasthours":"$recast_hours","user":{"id":"${USER.id}"}}""")
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        Log.d("kek", jsonObjectString)
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url("$BASE_URL/api/recasts").post(requestBody).build()

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
                    showToast("Переработка добавлена!")
                }
            }

        })
    }
}