package com.yrun.animequote

import com.yrun.animequote.databinding.ActivityMainBinding
import android.graphics.Color
import android.view.View

interface UiState {
    fun update(binding: ActivityMainBinding)

    data class Error(private val message: String) : UiState {
        override fun update(binding: ActivityMainBinding) {
            with(binding) {
                textView.text = message
                progressBar.visibility = View.GONE
                textView.setTextColor(Color.parseColor("#FF0000"))
                getButton.isEnabled = true
            }
        }
    }

    object Progress : UiState {
        override fun update(binding: ActivityMainBinding) {
            with(binding) {
                progressBar.visibility = View.VISIBLE
                textView.text = ""
                animeTextView.text = ""
                characterTextView.text = ""
                getButton.isEnabled = false
            }
        }
    }

    data class Success(
        private val quote: String = "",
        private val anime: String = "",
        private val character: String = ""
    ) :
        UiState {
        override fun update(binding: ActivityMainBinding) {
            with(binding) {
                animeTextView.text = "Anime: $anime"
                characterTextView.text = "Character: $character"
                textView.text = quote
                progressBar.visibility = View.GONE
                textView.setTextColor(Color.parseColor("#000000"))
                getButton.isEnabled = true
            }
        }
    }


    object Empty : UiState {
        override fun update(binding: ActivityMainBinding) = Unit
    }
}


