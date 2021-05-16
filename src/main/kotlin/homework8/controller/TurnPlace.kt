package homework8.controller

import kotlinx.serialization.Serializable

@Serializable
data class TurnPlace(val row: Int, val column: Int)
