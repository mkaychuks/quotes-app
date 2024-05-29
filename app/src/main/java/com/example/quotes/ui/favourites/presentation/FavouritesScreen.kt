package com.example.quotes.ui.favourites.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quotes.R
import com.example.quotes.ui.favourites.presentation.viewmodel.FavouritesEvent
import com.example.quotes.ui.favourites.presentation.viewmodel.FavouritesVM
import com.example.quotes.ui.search.presentation.screens.SearchResultAdviceBubble
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
) {

    val favouriteVM = hiltViewModel<FavouritesVM>()
    val uiState = favouriteVM.state.collectAsState().value
    val context = LocalContext.current


    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            resId = R.raw.hand_loading
        )
    )
    val composition2 by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            resId = R.raw.idle_search
        )
    )
    // the progress of the iteration
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(key1 = favouriteVM.channel) {
        favouriteVM.channel.collectLatest { show ->
            if (show) {
                Toast.makeText(context, "Advice removed from your Favourites", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(16.dp),
    ) {
        Text(
            text = "Favourites",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(24.dp))

        if (uiState.isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                ) {
                    LottieAnimation(composition = composition, progress = { progress })
                }
            }
        } else if (uiState.favourites.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                ) {
                    LottieAnimation(composition = composition2, progress = { progress })
                }
                Text(
                    text = "You have not yet added any advice to your Favourites",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.favourites.size) {
                    val fav = uiState.favourites[it]
                    SearchResultAdviceBubble(
                        adviceDesc = uiState.favourites[it].title,
                        adviceNumber = uiState.favourites[it].adviceNumber,
                        adviceDate = "",
                        onClick = {
                            favouriteVM.onEvent(
                                FavouritesEvent.DeleteFavourite(
                                    fav
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}