package com.github.pericladium

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

fun getRatingList(): SnapshotStateList<RatingIcon> {
    val ratingList = mutableStateListOf<RatingIcon>()
    ratingList.add(RatingIcon())
    ratingList.add(RatingIcon())
    ratingList.add(RatingIcon())
    ratingList.add(RatingIcon())
    ratingList.add(RatingIcon())
    return ratingList
}

@HiltViewModel
class RatingUIStateViewModel @Inject constructor() : ViewModel() {
    private var _ratingUIState = mutableStateOf(
        MultipleRatingBarUIState(
            mutableStateListOf(
                RatingUIState(
                    getRatingList()
                ),
                RatingUIState(
                    getRatingList()
                )
            )
        )
    )

    val ratingUIState = _ratingUIState
    var currentSelectionCount: MutableList<Int> = mutableListOf(0, 0)
    val maxListSize = getRatingList().size

    fun onUIEvent(event: UIEvent.InputEvents, position: Int) {
        when (event) {
            is UIEvent.InputEvents.UnSelect -> {
                // clear
                val ratingBar = _ratingUIState.value.snapshotRatingUIStateList[position]
                val modList = ratingBar.snapshotStateList
                for (i in 0 until maxListSize) {
                    modList[i] = modList[i].copy(
                        isSelected = false
                    )
                }
                currentSelectionCount[position] = 0
                onUIEvent(UIEvent.InputEvents.Select(event.id), position)
            }

            is UIEvent.InputEvents.Select -> {
                if (currentSelectionCount[position] <= event.id) {
                    currentSelectionCount[position] = event.id
                    val ratingBar = _ratingUIState.value.snapshotRatingUIStateList[position]
                    val label = ratingBar.displayLabel
                    val modList = ratingBar.snapshotStateList
                    for (i in 0 until currentSelectionCount[position] + 1) {
                        modList[i] = modList[i].copy(
                            isSelected = true
                        )
                    }
                    when (currentSelectionCount[position]) {
                        in 0..1 -> {
                            label.value = "Bad"
                        }

                        in 1..2 -> {
                            label.value = "Average"
                        }

                        in 2..4 -> {
                            label.value = "Good"
                        }
                    }
                }
            }
        }
    }
}
