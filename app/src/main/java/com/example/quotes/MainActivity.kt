package com.example.quotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quotes.ui.search.presentation.screens.SearchAdviceScreen
import com.example.quotes.ui.theme.QuotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuotesTheme {
                // A surface container using the 'background' color from the theme
//                HomeScreen()
                SearchAdviceScreen()
            }
        }
    }
}