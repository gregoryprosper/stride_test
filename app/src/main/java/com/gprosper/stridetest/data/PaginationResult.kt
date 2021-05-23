package com.gprosper.stridetest.data

/**
 * Created by gregprosper on 20,May,2021
 */
data class PaginationResult<out T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>,
)