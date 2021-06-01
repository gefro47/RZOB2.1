package com.example.rzob21.ApiInterface

import android.util.Log
import com.example.rzob21.utilits.*
import com.gefro.springbootkotlinRZOBbackend.models.Income
import com.gefro.springbootkotlinRZOBbackend.models.Recast
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.*

class IncomeApi {
    fun getIncome(date: Date){
        val request = Request.Builder().url("$BASE_URL/api/calc/${USER.id}/income/$date").build()

        val client = OkHttpClient()

        client.newCall(request).execute().use { response ->
            response.use {
                try {
                    val responseData = response.body?.string()
                    var json = Gson().fromJson(responseData, Income::class.java)
                    INCOME = json
                }catch (e: Exception){
                    Log.d("kek2", e.toString())
                }


            }
        }



//        client.newCall(request).enqueue(object: Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d("kek", e.toString())
//            }
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    if(response.code == 200) {
//                        val responseData = response.body?.string()
//                        var json = Gson().fromJson(responseData, Income::class.java)
//                        INCOME = json
//                        Log.d("kekInc", json.toString())
//                    }
//                }
//            }
//        })
    }

    fun getAllByYear (year: Int){

        val request = Request.Builder().url("$BASE_URL/api/${USER.id}/income/").build()

        val client = OkHttpClient()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d("kek", e.toString())
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    LIST_INCOME_OF_USER = mutableListOf()
//                    val responseData = response.body?.string()
//                    var json = Gson().fromJson(responseData, Array<Income>::class.java)
//                    for (i in json.indices) {
//                        LIST_INCOME_OF_USER.add(json[i])
////                            list.add(json[i])
////                            Log.d("kek", list[i].toString())
//                    }
//                    LIST_INCOME_OF_USER.sortedBy { it.month }.sortedBy { it.year }
//                    Log.d("list_income", LIST_INCOME_OF_USER.toString())
//                }
//            }
//        })
        client.newCall(request).execute().use { response ->
            response.use {
                try {
                    LIST_INCOME_OF_USER = mutableListOf()
                    val responseData = response.body?.string()
                    var json = Gson().fromJson(responseData, Array<Income>::class.java)
//                        if (json.isNotEmpty()){
                    for (i in json.indices){
                        if (json[i].year == year) {
                            LIST_INCOME_OF_USER.add(json[i])
                        }
                    }
                    LIST_INCOME_OF_USER.sortBy { it.month }
                    Log.d("list_income", LIST_INCOME_OF_USER.toString())
                }catch (e: Exception){
                    Log.d("kek2", e.toString())
                }
            }
        }
    }

    fun post(income: Double, month: Int, year: Int, calc: Boolean){
        //Это Post
        val jsonObject = JSONObject("""{
              "income_of_money": "$income",
              "month": "$month",
              "year": "$year",
              "math_calc": "$calc",
              "user": {
                "id": "${USER.id}"
              }
            }""")
        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()
        Log.d("kek", jsonObjectString)
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url("$BASE_URL/api/income").post(requestBody).build()

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
                    showToast("Зарплата добавлена!")
                }
            }

        })
    }
}