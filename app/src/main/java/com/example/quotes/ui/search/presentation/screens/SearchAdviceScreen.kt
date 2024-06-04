package com.example.quotes.ui.search.presentation.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quotes.R
import com.example.quotes.ui.favourites.data.Favourites
import com.example.quotes.ui.favourites.presentation.viewmodel.FavouritesEvent
import com.example.quotes.ui.favourites.presentation.viewmodel.FavouritesVM
import com.example.quotes.ui.search.presentation.viewmodel.AdviceSearchEvent
import com.example.quotes.ui.search.presentation.viewmodel.AdviceSearchVM
import com.example.quotes.ui.theme.QuotesTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchAdviceScreen(
    modifier: Modifier = Modifier,
) {
    var value by remember { mutableStateOf("") }
    val searchAdviceVM = hiltViewModel<AdviceSearchVM>()
    val uiState = searchAdviceVM.uiState.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val favouriteVM = hiltViewModel<FavouritesVM>()
    val context = LocalContext.current

    // setting up the lottie spec
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            resId = R.raw.hand_loading
        )
    )
    val composition2 by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            resId = R.raw.empty_state
        )
    )
    val composition3 by rememberLottieComposition(
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
            if(show){
                Toast.makeText(context, "Advice added to your Favourites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(16.dp),
    ) {
        // the textField
        SearchFieldWidget(
            fieldValue = value,
            onValueChange = {
                value = it
            },
            onSearch = {
                searchAdviceVM.onEvent(AdviceSearchEvent.SearchButtonClicked(value))
                value = ""
                keyboardController?.hide()
            },
            onSearchIconClicked = {
                searchAdviceVM.onEvent(AdviceSearchEvent.SearchButtonClicked(value))
                value = ""
                keyboardController?.hide()
            }
        )
        Spacer(Modifier.height(12.dp))
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
        } else if (uiState.idle) {
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
                    LottieAnimation(composition = composition3, progress = { progress })
                }
                Text(
                    text = "You can search for an advice by typing in the keyword in the search bar",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            if (uiState.dataReceived.slips == null) {
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
                        LottieAnimation(composition = composition2, progress = { progress })
                    }
                    Text(
                        text = "Sorry, we couldn't find an advice based on your search query",
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
                    items(uiState.dataReceived.slips.size) {
                        SearchResultAdviceBubble(
                            adviceDesc = uiState.dataReceived.slips[it].advice,
                            adviceNumber = uiState.dataReceived.slips[it].id,
                            adviceDate = uiState.dataReceived.slips[it].date,
                            onClick = {
                                favouriteVM.onEvent(
                                    FavouritesEvent.SaveFavourite(
                                        Favourites(
                                            title = uiState.dataReceived.slips[it].advice,
                                            adviceNumber = uiState.dataReceived.slips[it].id
                                        )
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SearchFieldWidget(
    fieldValue: String = "",
    onValueChange: (String) -> Unit,
    onSearch: KeyboardActionScope.() -> Unit,
    onSearchIconClicked: (String) -> Unit,
) {
    OutlinedTextField(
        value = fieldValue,
        onValueChange = onValueChange,
        trailingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "search icon",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .size(33.dp)
                    .clickable {
                        onSearchIconClicked(fieldValue)
                    }
            )
        },
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = "Search for advices using keywords e.g. 'car', 'house' ",
                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurface)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        keyboardActions = KeyboardActions(
            onSearch = onSearch
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        )
    )
}


@Composable
fun SearchResultAdviceBubble(
    modifier: Modifier = Modifier,
    adviceNumber: Int = 0,
    adviceDesc: String = "",
    adviceDate: String = "",
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(200.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Advice #$adviceNumber",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xff52FFA8),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                ),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = adviceDesc, textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontWeight = FontWeight.ExtraBold,
                ),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = adviceDate,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.ExtraBold,
                ),
            )
        }
        Icon(
            Icons.Default.Bookmark,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(
                Alignment.BottomEnd)
                .clickable { onClick() },
        )

    }
}


@Preview(name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun X() {
    QuotesTheme {
        SearchAdviceScreen()
    }
}