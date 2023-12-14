package com.example.androidexamenblog.data.remote

import com.example.androidexamenblog.data.entities.BlogEntry
import com.example.androidexamenblog.data.entities.BlogEntryRequest
import com.example.androidexamenblog.data.entities.EntryDetailResponse
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

    companion object{

        var service: BlogService? = null

        fun getInstance(): BlogService{
            if(service == null){

                val okhttpClient = OkHttpClient.Builder()

                okhttpClient.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                okhttpClient.connectTimeout(120, TimeUnit.SECONDS)
                okhttpClient.readTimeout(120, TimeUnit.SECONDS)

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpClient.build())
                    .build()

                service = retrofit.create(BlogService::class.java)
            }
            return service!!
        }

    }

    @GET("blog")
    suspend fun getBlogEntries(): Response<ArrayList<BlogEntry>>

    @POST("blog/create")
    suspend fun postEntry(@Body blogEntryRequest: BlogEntryRequest): Response<Void>

    @GET("blog/{identrada}")
    suspend fun getEntry(@Path("identrada") idEntrada: String): Response<EntryDetailResponse>

}