package com.yrun.animequote

interface LoadResult {
    fun handle(observable: UiObservable)

    data class Success(private val quote: String,private val anime: String,private val character: String ) : LoadResult {
        override fun handle(observable: UiObservable) {
            observable.updateUi(UiState.Success(quote,anime,character))
        }
    }

    data class Error(private val message: String) : LoadResult {
        override fun handle(observable: UiObservable) {
            observable.updateUi(UiState.Error(message))
        }
    }
}

