package com.github.pericladium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.pericladium.ui.theme.PericladiumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PericladiumTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiViewModel = viewModel(modelClass = RatingUIStateViewModel::class.java)
                    val uiState by remember {
                        uiViewModel.ratingUIState
                    }
                    RatingStar(uiState) { state ->
                        uiViewModel.onUIEvent(state)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingStar(uiState: RatingUIState, callback: (UIEvent.InputEvents) -> Unit) {
    val count = uiState.snapshotStateList.size
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        repeat(count) {
            Card(
                modifier = Modifier.size(60.dp).clickable {
                    val item = uiState.snapshotStateList[it]
                    if (item.isSelected) {
                        callback(UIEvent.InputEvents.Down(it))
                    } else {
                        callback(UIEvent.InputEvents.Up(it))
                    }
                },
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                val item = uiState.snapshotStateList[it]
                if (item.isSelected) {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 6.dp),
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "beer_bottle",
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 6.dp),
                        painter = painterResource(id = R.drawable.unstar),
                        contentDescription = "beer_bottle",
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                }
            }
        }
    }
}
