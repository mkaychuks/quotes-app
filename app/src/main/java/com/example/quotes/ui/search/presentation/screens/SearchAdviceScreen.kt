package com.example.quotes.ui.search.presentation.screens

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
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchAdviceScreen(
    modifier: Modifier = Modifier,
) {
    var value by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(16.dp)
    ) {
        // the textField
        SearchFieldWidget(
            fieldValue = value,
            onValueChange = {
                value = it
            },
            onSearch = {},
            onSearchIconClicked = {}
        )
        Spacer(Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(10) {
                SearchResultAdviceBubble(
                    adviceDesc = "Everybody makes mistakes.",
                    adviceNumber = 123,
                    adviceDate = "2016-05-10"
                )
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
                text = "Search for advices e.g. 'car', 'house' ",
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
    adviceDate: String = ""
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
            Spacer(modifier = Modifier.height(4.dp))
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
            Icons.Default.BubbleChart,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(
                Alignment.BottomEnd
            ),
        )
    }
}


//@Preview(name = "Light Mode")
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
//@Composable
//private fun X() {
//    QuotesTheme {
//        SearchAdviceScreen()
//    }
//}