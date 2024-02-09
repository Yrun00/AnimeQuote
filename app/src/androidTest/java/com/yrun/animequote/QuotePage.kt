package com.yrun.animequote

import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf

class QuotePage {
    fun checkVisible(quote:String) {
        onView(
            allOf(
                withId(R.id.textView),
                isAssignableFrom(LinearLayout::class.java)
            )
        ).check(matches(withText(quote)))
        onView(
            allOf(
                withId(R.id.getButton),
                withText("Get quote"),
                isAssignableFrom(LinearLayout::class.java),
                withParent(withId(R.id.rootLayout))
            )
        )
    }

    fun clickGet() {
        onView(
            allOf(
                withId(R.id.getButton),
                withText("Get quote"),
                isAssignableFrom(LinearLayout::class.java),
                withParent(withId(R.id.rootLayout))
            )
        ).perform(click())
    }

    fun checkError(message: String) {
        onView(
            allOf(
                withId(R.id.textView),
                isAssignableFrom(LinearLayout::class.java)
            )
        ).check(matches(withText(message)))
    }

    fun clickRetry() {
        onView(
            allOf(
                withId(R.id.retryButton),
                withText("Retry"),
                isAssignableFrom(LinearLayout::class.java),
                withParent(withId(R.id.rootLayout))
            )
        ).perform(click())
    }

}
