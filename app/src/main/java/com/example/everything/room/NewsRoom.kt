package com.example.everything.room

import android.content.Context
import androidx.room.Room

object NewsRoom {
    private var newsDao: NewsDao? = null

    fun getSearchDao(context: Context): NewsDao {
        if (newsDao == null) {
            val database = Room.databaseBuilder(context, AppDatabase::class.java, "favorite_news.db")
                .build()
            newsDao = database.newsDao()
        }
        return newsDao!!
    }
}