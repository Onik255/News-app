package com.example.everything.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.everything.data.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface Repository {
    suspend fun search(query: String, pageNumber: Int): ResponseData
}

class RepositoryImpl(private val context: Context, private val api: EverythingApi) : Repository {

    override suspend fun search(query: String, pageNumber: Int): ResponseData =
        performApiCall {
            api.search(query, pageNumber)
        }

    private suspend fun <K> performApiCall(
        apiCall: suspend () -> K
    ): ResponseData =
        withContext(Dispatchers.IO) {
            if (isInternetConnection()) {
                runCatching {
                    ResponseData.SuccessResponse(apiCall.invoke())
                }.recover {
                    ResponseData.FailedResponse.UnexpectedError(it)
                }.getOrThrow()
            } else ResponseData.FailedResponse.NoInternet
        }

    private fun isInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
}