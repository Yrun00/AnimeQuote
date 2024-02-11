package com.yrun.animequote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.yrun.animequote.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),ProvideViewModel {

    private lateinit var binding:ActivityMainBinding
    private lateinit var uiCallBack: UpdateUi
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = viewModel(MainViewModel::class.java)
        binding.getButton.setOnClickListener{
            mainViewModel.getQuote()}

        uiCallBack = object : UpdateUi {
            override fun updateUi(uiState: UiState) {
                uiState.update(binding)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.startGettingUpdates(uiCallBack)
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.stopGettingUpdates()
    }

    override fun <T : ViewModel> viewModel(clazz: Class<out T>): T =
        (application as ProvideViewModel).viewModel(clazz)
}

