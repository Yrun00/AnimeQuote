package com.yrun.animequote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yrun.animequote.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        uiCallBack = object : UpdateUi {
            override fun updateUi(uiState: UiState) {
                uiState.update(binding)
                viewModel.notifyObserved()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.getButton.setOnClickListener(viewmodel.getQuote())
        viewModel.StartGettingUpdates(uiCallBack)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}