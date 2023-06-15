package data

sealed class ServerResponse<out T> {

    object Loading : ServerResponse<Nothing>()
    data class Content<T>(val data: T) : ServerResponse<T>()
    data class Error(val error: Throwable) : ServerResponse<Nothing>()
}