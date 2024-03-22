package com.zo.kaijuadventure.data.model

import android.content.Context
import com.zo.kaijuadventure.R

data class ErrorHandling(
    val errorText: String,
    val errorActionText: String,
    val onErrorAction: () -> Unit
)

interface BaseError

fun BaseError.mapErrorMessage(context: Context) = when (this) {
    is QueryError -> this.message
    else -> context.getString(R.string.unknown_error_occurred)
}

fun BaseError.mapErrorHandling(context: Context, errorHandlingAction: () -> Unit): ErrorHandling = when (this) {
    is QueryError -> ErrorHandling(
        errorText = this.mapErrorMessage(context),
        errorActionText =  context.getString(R.string.retry),
        onErrorAction = { errorHandlingAction() }
    )
    else -> ErrorHandling(
        errorText = this.mapErrorMessage(context),
        errorActionText = context.getString(R.string.clear),
        onErrorAction = { errorHandlingAction() }
    )
}


data class QueryError(val message: String) : BaseError