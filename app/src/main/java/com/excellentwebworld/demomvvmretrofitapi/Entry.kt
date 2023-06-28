package com.excellentwebworld.demomvvmretrofitapi

data class Entry(
    val count: Int,
    val entries: List<EntryDetails>
)

data class EntryDetails (
    val API: String,
    val Description: String,
    val Auth: String,
    val HTTPS: Boolean,
    val Cors: String,
    val Link: String,
    val Category: String,
)