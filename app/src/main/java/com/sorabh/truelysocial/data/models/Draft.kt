package com.sorabh.truelysocial.data.models

data class Draft(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)