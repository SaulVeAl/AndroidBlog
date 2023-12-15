package com.example.androidexamenblog.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entrada_blog")
data class BlogEntryDB(
    @PrimaryKey
    @ColumnInfo(name = "identrada") val idEntry: String,
    @ColumnInfo(name = "titulo") val title: String?,
    @ColumnInfo(name = "autor") val author: String?,
    @ColumnInfo(name= "contenido") val content: String?,
    @ColumnInfo(name = "fecha") val date: Long?
)
