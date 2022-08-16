package com.timmytruong.template.data.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.timmytruong.template.BuildConfig
import com.timmytruong.template.data.model.adapter.SectionAdapter
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkProvider {

    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var moshi: Moshi? = null

    fun getOkHttpClient() = okHttpClient ?: OkHttpClient()
        .newBuilder()
        .addAuthorizationInterceptor()
        .addLoggingInterceptor()
        .build()
        .apply { okHttpClient = this }

    fun getRetrofitService() = retrofit ?: Retrofit.Builder()
        .client(getOkHttpClient())
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
        .build()
        .apply { retrofit = this }

    private fun OkHttpClient.Builder.addAuthorizationInterceptor(): OkHttpClient.Builder {
        return addInterceptor {
            val request = it.request()
            val newUrl = request.url.newBuilder().addQueryParameter("api-key", BuildConfig.API_KEY).build()
            val newRequest = request.newBuilder().url(newUrl).build()
            return@addInterceptor it.proceed(newRequest)
        }
    }

    private fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder {
        return addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        )
    }

    private fun getMoshi(): Moshi = moshi ?: Moshi.Builder().add(SectionAdapter()).build().apply { moshi = this }
}