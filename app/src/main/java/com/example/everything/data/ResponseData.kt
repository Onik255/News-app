package com.example.everything.data

sealed class ResponseData {
    data class SuccessResponse<out K>(val response: K) : ResponseData()
    sealed class FailedResponse : ResponseData() {
        data object NoInternet : FailedResponse()
        data object HttpException : FailedResponse()
        data class UnexpectedError(val error: Throwable?) : FailedResponse()
    }
}
