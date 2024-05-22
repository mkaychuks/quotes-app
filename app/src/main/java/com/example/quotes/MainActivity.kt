package com.example.quotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.ui.home.RetrofitInstance
import com.example.quotes.ui.home.domain.AdviceRepositoryImpl
import com.example.quotes.ui.home.presentation.screen.HomeScreen
import com.example.quotes.ui.home.presentation.viewmodel.HomeScreenVM
import com.example.quotes.ui.theme.QuotesTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeScreenVM>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeScreenVM(AdviceRepositoryImpl(RetrofitInstance.api)) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel
            QuotesTheme {
                // A surface container using the 'background' color from the theme
                HomeScreen()
            }
        }
    }
}