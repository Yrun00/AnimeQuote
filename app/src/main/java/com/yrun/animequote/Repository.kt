package com.yrun.animequote

import java.net.UnknownHostException

interface Repository {

    suspend fun loadData(): LoadResult

    class Base(private val service: QuoteService) : Repository {

        override suspend fun loadData(): LoadResult = try {
            val response = service.load().execute()
            val body = response.body()!!
            body.result()
        } catch (t: Exception) {
            if (t is UnknownHostException)
                LoadResult.Error("No internet connection")
            else
                LoadResult.Error("Service unavailable")
        }
    }
}
