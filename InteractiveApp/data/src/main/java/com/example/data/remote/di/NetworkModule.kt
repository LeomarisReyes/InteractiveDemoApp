package com.example.data.remote.di

import com.example.data.BuildConfig
import com.example.data.remote.apis.ColombiaPresidentsApi
import com.example.data.remote.repository.ColombiaPresidentRepository
import com.example.data.remote.repository.implementations.ColombiaPresidentRepositoryImpl
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun presidentsRepository(
        repository: ColombiaPresidentRepositoryImpl
    ) : ColombiaPresidentRepository

    companion object {

        @Provides
        @Singleton
        fun provideGson() :Gson {
            return Gson()
        }

        @Provides
        fun provideRetrofit(gson: Gson): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        @Provides
        fun provideColombiaPresidentApiService(retrofit: Retrofit): ColombiaPresidentsApi {
            return retrofit.create(ColombiaPresidentsApi::class.java)
        }

    }
}
