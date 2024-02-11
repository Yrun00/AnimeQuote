package com.yrun.animequote


interface UiObservable : UpdateObserver, UpdateUi {

    class Base : UiObservable {

        private var cache: UiState = UiState.Empty
        private var observer: UpdateUi = UpdateUi.Empty

        override fun updateObserver(observer: UpdateUi) {
            this.observer = observer
            observer.updateUi(cache)
        }

        override fun updateUi(uiState: UiState) {
            cache = uiState
            observer.updateUi(cache)
        }
    }
}

interface UpdateObserver {
    fun updateObserver(observer: UpdateUi)
}

interface UpdateUi {
    fun updateUi(uiState: UiState)

    object Empty : UpdateUi {
        override fun updateUi(uiState: UiState) = Unit
    }
}
