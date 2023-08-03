package com.myapp.newsmvvmappdemo.network

import okhttp3.ResponseBody

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ActionResult<out R> {
    data class Success<out T>(val data: T) : ActionResult<T>()
    data class Warning<out T>(val message: String) : ActionResult<T>()
    data class Error(val exception: ResponseBody?) : ActionResult<Nothing>()
    object Loading : ActionResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Warning -> "Warning[message=$message]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}


