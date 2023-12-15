package com.example.androidexamenblog.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidexamenblog.data.entities.BlogEntryDB

@Dao
interface BlogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEntradas(entradas: List<BlogEntryDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEntrada(entrada: BlogEntryDB)

    @Query("SELECT * FROM entrada_blog")
    fun obtenerEntradas(): LiveData<List<BlogEntryDB>>

}