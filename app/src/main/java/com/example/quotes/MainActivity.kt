package com.example.quotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quotes.ui.home.presentation.screen.HomeScreen
import com.example.quotes.ui.home.presentation.viewmodel.HomeScreenVM
import com.example.quotes.ui.theme.QuotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val viewModel by viewModels<HomeScreenVM>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory{
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return HomeScreenVM(AdviceRepositoryImpl(RetrofitInstance.api)) as T
//                }
//            }
//        }
//    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm = hiltViewModel<HomeScreenVM>()
            QuotesTheme {
                // A surface container using the 'background' color from the theme
                HomeScreen()
            }
        }
    }
}