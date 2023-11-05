package com.example.everything.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.everything.data.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("select * from news_table")
    fun getAll(): Flow<List<NewsEntity>>

    @Query("delete from news_table where title = :newTitle")
    fun deleteByTitle(newTitle: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(searchResult: NewsEntity): Long
}