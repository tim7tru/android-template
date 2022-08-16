package com.timmytruong.template.di

import com.squareup.moshi.Moshi
import com.timmytruong.template.BuildConfig
import com.timmytruong.template.data.model.adapter.SectionAdapter
import com.timmytruong.template.data.remote.TopStoriesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient()
        .newBuilder()
        .addAuthorizationInterceptor()
        .addLoggingInterceptor()
        .build()

    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun provideMoshi(sectionAdapter: SectionAdapter) = Moshi.Builder()
        .add(sectionAdapter)
        .build()

    @Singleton
    @Provides
    fun provideTopStoriesService(retrofit: Retrofit) = retrofit.create(TopStoriesService::class.java)

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
}