package homework8.model

import homework8.controller.TurnAuthor
import homework8.controller.TurnPlace
import javafx.scene.control.Button

interface ModelInterface {
    fun move(
        turnPlace: TurnPlace,
        gameField: List<List<Button>>,
        turnAuthor: TurnAuthor = TurnAuthor.CLIENT
    ): MoveResult
}
