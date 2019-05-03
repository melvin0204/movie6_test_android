package com.movie.six.coding.test

data class Pagination(
    val comments: List<Any>,
    val page: Int,
    val size: Int,
    val total: Int
)