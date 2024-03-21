package com.zo.kaijuadventure.data.model

interface BaseError

fun BaseError.mapBaseError() = when (this) {
    is QueryError -> this.message
    else -> "Unknown Error Occurred"
}


data class QueryError(val message: String) : BaseError