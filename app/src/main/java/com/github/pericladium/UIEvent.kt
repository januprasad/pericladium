package com.github.pericladium

sealed class UIEvent {
    sealed class InputEvents() {
        class Select(val id: Int) : InputEvents()
        class UnSelect(val id: Int) : InputEvents()
    }
}
