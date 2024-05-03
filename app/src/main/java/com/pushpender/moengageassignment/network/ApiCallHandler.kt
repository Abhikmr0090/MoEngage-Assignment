package com.pushpender.moengageassignment.network

import com.pushpender.moengageassignment.BuildConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val error: T)  : ApiResult<T>()
    data object Loading                : ApiResult<Nothing>()
}

object ApiCall {

    suspend inline fun <reified T> fetchNews(requestType: String, endPoint: String): ApiResult<T> {
        return withContext(Dispatchers.IO) {

            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            try {
                val url = URL("${BuildConfig.BASE_URL}/$endPoint")
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = requestType

                val inputStream = connection.inputStream
                reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                // Use TypeToken to capture the generic type T at runtime for Gson
                val dataType = object : TypeToken<T>() {}.type
                val data: T = Gson().fromJson(response.toString(), dataType)

                ApiResult.Success(data)
            } catch (e: Exception) {
                e.printStackTrace()
                ApiResult.Error(e.message as T)
            } finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}



