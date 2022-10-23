package com.sorabh.truelysocial.data.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sorabh.truelysocial.BuildConfig
import com.sorabh.truelysocial.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NodeApiClient {

    private var gson: Gson = GsonBuilder().create()

    private var sNodeApiInterface: NodeApiInterface = client.create(
        NodeApiInterface::class.java
    )

    @JvmStatic
    val nodeApiInterface: NodeApiInterface
        get() {
            return sNodeApiInterface
        }


    private val client: Retrofit
        get() {
            val okHttpBuilder = OkHttpClient.Builder()


            okHttpBuilder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            )

            okHttpBuilder.connectTimeout(20, TimeUnit.SECONDS)
            okHttpBuilder.readTimeout(20, TimeUnit.SECONDS)
            okHttpBuilder.writeTimeout(20, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl(Constants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpBuilder.build())
                .build()
        }
}
