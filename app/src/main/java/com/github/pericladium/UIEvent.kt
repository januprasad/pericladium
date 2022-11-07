package com.github.pericladium

sealed class UIEvent {
    sealed class InputEvents() {
        class Up(val id: Int) : InputEvents()
        class Down(val id: Int) : InputEvents()
    }
}
