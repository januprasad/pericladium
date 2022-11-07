package com.github.pericladium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.pericladium.ui.theme.PericladiumTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
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

                    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                        repeat(uiState.snapshotRatingUIStateList.size) { item ->
                            RatingApp(uiState.snapshotRatingUIStateList[item]) {
                                uiViewModel.onUIEvent(it, item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun RatingApp(ratingUIState: RatingUIState, callback: (UIEvent.InputEvents) -> Unit) {
    RatingStar(ratingUIState) { state ->
        callback(state)
    }
    Text(
        text = ratingUIState.displayLabel.value,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = FontFamily.Serif
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RatingStar(uiState: RatingUIState, callback: (UIEvent.InputEvents) -> Unit) {
    val count = uiState.snapshotStateList.size
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        repeat(count) {
            val item = uiState.snapshotStateList[it]
            Box(
                modifier = Modifier.size(60.dp).clickable {
                    if (item.isSelected) {
                        callback(UIEvent.InputEvents.UnSelect(it))
                    } else {
                        callback(UIEvent.InputEvents.Select(it))
                    }
                },
                contentAlignment = Alignment.Center
            ) {
                val icon = if (item.isSelected) {
                    R.drawable.star
                } else {
                    R.drawable.unstar
                }
                AnimatedContent(
                    targetState = item,
                    transitionSpec = {
                        addAnimation().using(
                            SizeTransform(clip = false)
                        )
                    }
                ) { value ->
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(top = 6.dp),
                        painter = painterResource(id = icon),
                        contentDescription = "beer_bottle",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 800): ContentTransform {
    return slideInVertically(
        animationSpec = tween(durationMillis = duration)
    ) { height -> -height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(
        animationSpec = tween(durationMillis = duration)
    ) { height -> height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}
