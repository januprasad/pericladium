package com.github.pericladium

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList

@Stable
data class RatingUIState(
    val snapshotStateList: SnapshotStateList<RatingIcon>
)

data class RatingIcon(
    var isSelected: Boolean = false
)
