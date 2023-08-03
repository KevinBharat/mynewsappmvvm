package com.myapp.newsmvvmappdemo.network

import com.google.gson.GsonBuilder
import com.myapp.newsmvvmappdemo.BuildConfig
import com.myapp.newsmvvmappdemo.utils.Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface WebApiClient {

    @GET("top-headlines/category/health/in.json")
    suspend fun getNews(): NewsResponse


    companion object {
        val instance: WebApiClient by lazy {
            val gson = GsonBuilder().create()

            val logging = HttpLoggingInterceptor()
            logging.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC

            val httpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .retryOnConnectionFailure(true)
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()

            retrofit.create(WebApiClient::class.java)
        }
    }
}