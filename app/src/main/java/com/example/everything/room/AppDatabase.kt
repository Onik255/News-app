package com.example.everything.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.everything.data.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}