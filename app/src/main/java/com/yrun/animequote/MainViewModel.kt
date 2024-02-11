package com.yrun.animequote

class MainViewModel(
    private val observable: UiObservable,
    private val repository: Repository,
    runAsync: RunAsync
): BaseViewModel(runAsync) {

    fun getQuote() {
        observable.updateUi(UiState.Progress)
        runAsync({
            repository.loadData()
        }){ loadResult->
            loadResult.handle(observable)
        }
    }

    fun startGettingUpdates(uiCallBack: UpdateUi) {
        observable.updateObserver(uiCallBack)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty)
    }

    fun init(isFirstOpen: Boolean) {
        if (isFirstOpen) observable.updateUi(UiState.Success())
    }
}