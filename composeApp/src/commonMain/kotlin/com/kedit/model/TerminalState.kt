package com.kedit.model

data class TerminalState(

    val history: List<String> = listOf(),

    val currentInput: String = "",

    val isVisible: Boolean = true,

    val height: Float = 220f
)