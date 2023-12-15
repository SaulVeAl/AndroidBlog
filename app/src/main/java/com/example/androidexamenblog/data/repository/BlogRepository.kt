package com.example.androidexamenblog.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.androidexamenblog.data.entities.BlogEntryDB
import com.example.androidexamenblog.data.entities.BlogEntryRequest
import com.example.androidexamenblog.data.local.BlogDao
import com.example.androidexamenblog.data.remote.BlogRemoteDataSource
import javax.inject.Inject

class BlogRepository @Inject constructor(
    private val remoteDataSource: BlogRemoteDataSource,
    private val localDataSource: BlogDao
){

    suspend fun obtenerEntradas() = remoteDataSource.obtenerEntradas()

    suspend fun obtenerEntrada(idEntrada: String) = remoteDataSource.obtenerEntrada(idEntrada)

    suspend fun obtenerEntradasLocal() = localDataSource.obtenerEntradas()

    suspend fun guardarEntradas(entradas: List<BlogEntryDB>) = localDataSource.insertarEntradas(entradas)

    suspend fun registrarEntrada(entrada: BlogEntryRequest) = remoteDataSource.registrarEntrada(entrada)

}