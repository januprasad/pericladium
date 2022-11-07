package com.github.pericladium

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList


@Stable
data class MultipleRatingBarUIState(
    val snapshotRatingUIStateList: SnapshotStateList<RatingUIState>,
)

data class RatingUIState(
    val snapshotStateList: SnapshotStateList<RatingIcon>,
    val displayLabel: MutableState<String> = mutableStateOf("")
)

data class RatingIcon(
    var isSelected: Boolean = false
)
