package com.example.everything.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.everything.data.News
import com.example.everything.data.toNewsClient
import com.example.everything.room.NewsRoom
import com.example.everything.room.RepositoryDb
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {
    private val dbRepository: RepositoryDb by lazy {
        RepositoryDb(NewsRoom.getSearchDao(getApplication()))
    }

    val favorites: StateFlow<List<News>> =
        dbRepository.getAllDb().map { it.map { it.toNewsClient() } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun deleteFromDb(result: News) {
        viewModelScope.launch {
            dbRepository.deleteByTitle(result.title)
        }
    }
}