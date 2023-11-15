package com.example.everything.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.everything.data.News
import com.example.everything.data.ResponseData
import com.example.everything.data.SearchResponse
import com.example.everything.data.SearchResult
import com.example.everything.data.toNewsClient
import com.example.everything.data.toNewsEntity
import com.example.everything.net.Repository
import com.example.everything.net.RepositoryImpl
import com.example.everything.net.RetrofitClient.getEverythingApi
import com.example.everything.room.NewsRoom.getSearchDao
import com.example.everything.room.RepositoryDb
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: Repository by lazy {
        RepositoryImpl(
            getApplication(),
            getEverythingApi()
        )
    }
    private val dbRepository: RepositoryDb by lazy {
        RepositoryDb(getSearchDao(getApplication()))
    }

    private val _searchResult = MutableStateFlow<List<News>>(emptyList())
    val searchResult: StateFlow<List<News>> = _searchResult.asStateFlow()

    private val _searchError = MutableSharedFlow<ResponseData.FailedResponse>()
    val searchError: SharedFlow<ResponseData.FailedResponse> = _searchError.asSharedFlow()

    private val _favoriteError = MutableSharedFlow<Unit>()
    val favoriteError: SharedFlow<Unit> = _favoriteError.asSharedFlow()

    var isLoading = false
    var isLastPage = false
    var lastPageNumber = 1
    var lastQ = ""

    fun search(q: String, pageNumber: Int = 1, isNewSearch: Boolean = true, pageSize: Int = 100) {
        isLoading = true
        viewModelScope.launch {
            lastQ = q
            when (val resp = repository.search(q, pageNumber, pageSize)) {
                is ResponseData.SuccessResponse<*> -> {
                    if (resp.response is SearchResponse) {
                        if (isNewSearch) {
                            _searchResult.value =
                                resp.response.articles.mapNotNull { it.toNewsClient() }
                        } else {
                            _searchResult.update {
                                it + resp.response.articles.mapNotNull { it.toNewsClient() }
                            }
                        }
                        lastPageNumber = pageNumber

                        val totalResults = resp.response.totalResults!!
                        isLastPage = totalResults/pageSize >= lastPageNumber
                    } else {
                        error("the return type of the search() is wrong")
                    }
                }

                is ResponseData.FailedResponse -> {
                    Log.e("DDDDDDDDDD", "$resp")
                    _searchError.emit(resp)
                }
            }
        }
    }

    fun addDb(result: News) {
        viewModelScope.launch {
            dbRepository.addDb(result.toNewsEntity()).also {
                if (it == -1L) _favoriteError.emit(Unit)
            }
        }
    }
}