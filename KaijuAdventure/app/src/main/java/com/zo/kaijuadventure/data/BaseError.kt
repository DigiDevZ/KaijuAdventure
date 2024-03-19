package com.zo.kaijuadventure.data

interface BaseError

fun BaseError.mapBaseError() = when (this) {
    //Sealed class would be stronger here
    is QueryError -> this.message
    else -> "Unknown Error Occurred"
}


data class QueryError(val message: String) : BaseError