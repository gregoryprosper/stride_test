package com.gprosper.stridetest.util

import java.lang.Exception

/**
 * Created by gregprosper on 20,May,2021
 */
sealed class Resource<T>() {
    class Loading<T>(val data: T? = null) : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val message: String, val data: T? = null) : Resource<T>()
}
