package com.meharenterprises.originsms.connect.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.meharenterprises.originsms.connect.data.local.db.ConnectDatabase
import com.meharenterprises.originsms.connect.data.remote.api.ConnectApiService
import com.meharenterprises.originsms.connect.data.remote.socket.ConnectSocketClient
import com.meharenterprises.originsms.connect.data.repository.AuthRepositoryImpl
import com.meharenterprises.originsms.connect.data.repository.MessageRepositoryImpl
import com.meharenterprises.originsms.connect.data.repository.ContactRepositoryImpl
import com.meharenterprises.originsms.connect.domain.repository.ContactRepository
import com.meharenterprises.originsms.connect.domain.repository.AuthRepository
import com.meharenterprises.originsms.connect.domain.repository.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Replace with your VPS URL — loaded from BuildConfig or remote config
private const val BASE_URL = "https://your-vps-domain.com/api/v1/"

@Module
@InstallIn(SingletonComponent::class)
object ConnectNetworkModule {

    @Provides @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides @Singleton
    fun provideApiService(retrofit: Retrofit): ConnectApiService =
        retrofit.create(ConnectApiService::class.java)

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): ConnectDatabase =
        ConnectDatabase.getInstance(ctx)

    @Provides @Singleton
    fun provideSocketClient(gson: Gson): ConnectSocketClient = ConnectSocketClient(gson)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectRepositoryModule {

    @Binds @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds @Singleton
    abstract fun bindMessageRepository(impl: MessageRepositoryImpl): MessageRepository

    @Binds @Singleton
    abstract fun bindContactRepository(impl: ContactRepositoryImpl): ContactRepository
}
