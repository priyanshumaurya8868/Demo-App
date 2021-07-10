package com.priyanshumaurya8868.demoapp.di

import com.priyanshumaurya8868.demoapp.Constant
import com.priyanshumaurya8868.demoapp.api.DirectoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
 fun  provideDirectoryApi() =  Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(DirectoryApi::class.java)


}
