package com.example.androidexamenblog.di

import android.content.Context
import com.example.androidexamenblog.data.local.BlogDao
import com.example.androidexamenblog.data.local.BlogDatabase
import com.example.androidexamenblog.data.remote.BlogRemoteDataSource
import com.example.androidexamenblog.data.remote.BlogService
import com.example.androidexamenblog.data.repository.BlogRepository
import com.example.androidexamenblog.utils.Constants
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

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideLogginInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient().newBuilder().addInterceptor(logging).connectTimeout(60,TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideBlogService(retrofit: Retrofit): BlogService{
        return retrofit.create(BlogService::class.java)
    }

    @Provides
    @Singleton
    fun provideBlogRemoteDataSource(blogService: BlogService): BlogRemoteDataSource{
        return BlogRemoteDataSource(blogService)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) = BlogDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideBlogDao(db: BlogDatabase) = db.blogDao()

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: BlogRemoteDataSource, localDataSource: BlogDao) = BlogRepository(remoteDataSource,localDataSource)

}