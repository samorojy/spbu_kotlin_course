package homework8.model

import homework8.controller.TurnStage

data class MoveResult(val turnStage: TurnStage, val isGameOver: Boolean)
