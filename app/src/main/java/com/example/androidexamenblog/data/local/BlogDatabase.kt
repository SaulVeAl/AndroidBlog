package com.example.androidexamenblog.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidexamenblog.data.entities.BlogEntryDB

@Database(entities = [BlogEntryDB::class], version = 1, exportSchema = false)
abstract class BlogDatabase: RoomDatabase() {

    abstract fun blogDao(): BlogDao

    companion object {
        @Volatile private var instance: BlogDatabase? = null

        fun getDatabase(context: Context): BlogDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, BlogDatabase::class.java, "blog")
                .fallbackToDestructiveMigration()
                .build()
    }

}