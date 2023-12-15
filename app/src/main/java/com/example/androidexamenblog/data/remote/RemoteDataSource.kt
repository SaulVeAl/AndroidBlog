package com.example.androidexamenblog.data.remote

import android.util.Log
import com.example.androidexamenblog.utils.Resource
import retrofit2.Response

abstract class RemoteDataSource {

    protected suspend fun <T> getNetworkResult(call: suspend () -> Response<T>): Resource<T>{

        try{
            val response = call()
            if(response.isSuccessful){
                val body = response.body()
                if(body != null) return Resource.Success(body)
            }
            return error("${response.code()} - ${response.message()}")
        }catch (ex: Exception){
            return error(ex.message ?: ex.toString())
        }

    }

    private fun <T> error(msj: String): Resource<T>{
        Log.d("RemoteDataSource","msj")
        return Resource.Error("Error de red: $msj")
    }

}