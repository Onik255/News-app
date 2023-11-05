package com.example.everything.room

import com.example.everything.data.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryDb(private val dao: NewsDao) {

    suspend fun addDb(result: NewsEntity) =
        withContext(Dispatchers.IO) {
            dao.insert(result)
        }

    fun getAllDb() = dao.getAll()

    suspend fun deleteByTitle(title: String) =
        withContext(Dispatchers.IO) {
            dao.deleteByTitle(title)
        }
}