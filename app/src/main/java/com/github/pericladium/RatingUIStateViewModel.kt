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
    private var _ratingUIState = mutableStateOf(RatingUIState(getRatingList()))
    val ratingUIState = _ratingUIState

    var currentSelectionCount = 0
    val maxListSize = getRatingList().size

    fun onUIEvent(event: UIEvent.InputEvents) {
        when (event) {
            is UIEvent.InputEvents.Down -> {
                // clear
                val modList = ratingUIState.value.snapshotStateList
                for (i in 0 until maxListSize) {
                    modList[i] = modList[i].copy(
                        isSelected = false
                    )
                }
                _ratingUIState.value = ratingUIState.value.copy(
                    snapshotStateList = modList
                )
                currentSelectionCount = 0
                onUIEvent(UIEvent.InputEvents.Up(event.id))
            }

            is UIEvent.InputEvents.Up -> {
                if (currentSelectionCount <= event.id) {
                    currentSelectionCount = event.id
                    val modList = ratingUIState.value.snapshotStateList
                    for (i in 0 until currentSelectionCount + 1) {
                        modList[i] = modList[i].copy(
                            isSelected = true
                        )
                    }
                    _ratingUIState.value = ratingUIState.value.copy(
                        snapshotStateList = modList
                    )
                }
            }
        }
    }
}
