package com.example.androidexamenblog.data.remote

import com.example.androidexamenblog.data.entities.BlogEntry
import com.example.androidexamenblog.data.entities.BlogEntryRequest
import com.example.androidexamenblog.data.entities.EntryDetailResponse
import com.example.androidexamenblog.data.entities.EntryRegisterResponse
import com.example.androidexamenblog.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface BlogService {

    @GET("blog")
    suspend fun getBlogEntries(): Response<ArrayList<BlogEntry>>

    @POST("blog/create")
    suspend fun postEntry(@Body blogEntryRequest: BlogEntryRequest): Response<EntryRegisterResponse>

    @GET("blog/{identrada}")
    suspend fun getEntry(@Path("identrada") idEntrada: String): Response<EntryDetailResponse>

}