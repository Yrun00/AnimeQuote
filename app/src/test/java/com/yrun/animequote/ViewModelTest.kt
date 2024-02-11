package com.yrun.animequote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MainViewModelUnitTest {

    private lateinit var runAsync: FakeRunAsync
    private lateinit var observable: FakeUiObservable
    private lateinit var mainViewModel: com.yrun.animequote.MainViewModel
    private lateinit var repository: FakeRepository

    @Before
    fun setup() {
        runAsync = FakeRunAsync()
        observable = FakeUiObservable()
        repository = FakeRepository()
        mainViewModel = MainViewModel(
            observable = observable,
            repository = repository,
            runAsync = runAsync
        )
    }

    @Test
    fun testFirstRun() {
        mainViewModel.init(isFirstOpen = true)
        assertEquals(listOf(UiState.Success(quote = "")), observable.states)
        val uiCallBack = object : UpdateUi {
            override fun updateUi(uiState: UiState) = Unit
        }
        mainViewModel.startGettingUpdates(uiCallBack)
        assertEquals(listOf(uiCallBack), observable.observers)
        mainViewModel.stopGettingUpdates()
        assertEquals(listOf(uiCallBack, UpdateUi.Empty), observable.observers)
    }

    @Test
    fun testNotFirstRun() {
        mainViewModel.init(isFirstOpen = false)
        assertEquals(emptyList<UiState>(), observable.states)
    }

    @Test
    fun testSuccess() {
        repository.result = LoadResult.Success(
            quote = "Why does everything you say makes me wanna bash your face in?",
            anime = "Yu Yu Hakusho",
            character = "Kazuma "
        )
        mainViewModel.getQuote()
        assertEquals(listOf(UiState.Progress), observable.states)
        runAsync.returnResult()
        assertEquals(
            listOf(
                UiState.Progress,
                UiState.Success(quote = "Why does everything you say makes me wanna bash your face in?",
                    anime = "Yu Yu Hakusho",
                    character = "Kazuma ")
            ), observable.states
        )
    }

    @Test
    fun testError() {
        repository.result = LoadResult.Error(message = "No internet connection")
        mainViewModel.getQuote()
        assertEquals(listOf(UiState.Progress), observable.states)
        runAsync.returnResult()
        assertEquals(
            listOf(
                UiState.Progress,
                UiState.Error(message = "No internet connection")
            ), observable.states
        )
    }
}

class FakeRunAsync : RunAsync {

    private var cachedUiBlock: (Any) -> Unit = {}
    private var cachedResult: Any = ""

    fun returnResult() {
        cachedUiBlock.invoke(cachedResult)
    }

    override fun <T : Any> start(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        uiBlock: (T) -> Unit
    ) = runBlocking {
        val result = background.invoke()
        cachedResult = result
        cachedUiBlock = uiBlock as (Any) -> Unit
    }
}

private class FakeUiObservable : UiObservable {

    val states = mutableListOf<UiState>()
    val observers = mutableListOf<UpdateUi>()

    override fun updateUi(uiState: UiState) {
        states.add(uiState)
    }

    override fun updateObserver(observer: UpdateUi) {
        observers.add(observer)
    }
}

private class FakeRepository : Repository {

    lateinit var result: LoadResult
    override suspend fun loadData() = result
}