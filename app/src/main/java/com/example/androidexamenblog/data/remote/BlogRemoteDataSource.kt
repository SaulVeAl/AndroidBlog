package com.example.androidexamenblog.data.remote

import com.example.androidexamenblog.data.entities.BlogEntryRequest
import com.example.androidexamenblog.data.local.BlogDao
import javax.inject.Inject

class BlogRemoteDataSource @Inject constructor(
    private val blogService: BlogService
): RemoteDataSource() {

    suspend fun obtenerEntradas() = blogService.getBlogEntries()

    suspend fun obtenerEntrada(idEntrada: String) = blogService.getEntry(idEntrada)

    suspend fun registrarEntrada(blogEntryRequest: BlogEntryRequest) = blogService.postEntry(blogEntryRequest)

}