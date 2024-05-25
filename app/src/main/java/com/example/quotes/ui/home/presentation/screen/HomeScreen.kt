package com.example.quotes.ui.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.quotes.R
import com.example.quotes.ui.home.presentation.viewmodel.HomeScreenVM

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val homeScreenVM = hiltViewModel<HomeScreenVM>()
    val uiState = homeScreenVM.homeScreenUiState.collectAsState().value

    // setting up the lottie spec
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            resId = R.raw.hand_loading
        )
    )
    val composition1 by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            resId = R.raw.error
        )
    )
    // the progress of the iteration
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // check for the loading screen time for the state
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            ) {
                LottieAnimation(composition = composition, progress = { progress })
            }
        } else if (uiState.errorState) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            ) {
                LottieAnimation(composition = composition1, progress = { progress })
            }
        } else {
            ConstraintLayout {
                val (quoteBox, balloon) = createRefs()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(315.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(start = 20.dp, top = 38.dp, end = 20.dp)
                        .constrainAs(quoteBox) {

                        },
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "ADVICE #${uiState.adviceData.slip?.id}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xff52FFA8),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "\"${uiState.adviceData.slip?.advice}\"",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.inverseSurface,
                                fontWeight = FontWeight.ExtraBold,
                            ),
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Divider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.weight(2f)
                            )
                            Icon(
                                Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface,
                                modifier = Modifier
                                    .weight(1f)
                                    .size(40.dp)
                            )
                            Divider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.weight(2f)
                            )
                        }
                    }
                }

                // the Balloon and qcode
                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xff52FFA8))
                        .constrainAs(balloon) {
                            top.linkTo(quoteBox.bottom, margin = -25.dp)
                            start.linkTo(quoteBox.start)
                            end.linkTo(quoteBox.end)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.QrCode2,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(33.dp)
                    )
                }

            }
        }
    }
}