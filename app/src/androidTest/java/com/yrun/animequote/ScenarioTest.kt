package com.yrun.animequote

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ScenarioTest {
    @Test
    fun errorSuccess() {
        val quotePage = QuotePage()
        quotePage.checkVisible("")
        quotePage.clickGet()
        quotePage.checkError(message = "No internet connection")
        quotePage.clickRetry()
        quotePage.checkVisible(quote = "How far do you want me to suppress myself so that you'll be satisfied?")
    }
}