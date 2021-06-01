package test2.game

import javafx.scene.control.Button
import tornadofx.App
import tornadofx.View
import tornadofx.borderpane
import tornadofx.button
import tornadofx.gridpane
import tornadofx.label
import tornadofx.row
import tornadofx.textfield
import tornadofx.useMaxWidth
import tornadofx.vbox

class TicTacToe : App(StartView::class)

class StartView : View("Find Pair: menu") {
    private val controller: Controller by inject()
    override val root = vbox {
        borderpane { center = label("Enter size of the game") }
        val textField = textfield("4")
        borderpane {
            center = button("Start Game") {
                setOnAction {
                    try {
                        controller.setSize(textField.text.toInt())
                        controller.startGame()
                    } catch (e: NumberFormatException) {
                        println("Required to enter a number. \n Error: ${e.message}")
                    } catch (e: IllegalArgumentException) {
                        println("Error: ${e.message}")
                    }
                }
                useMaxWidth = true
            }
        }
    }
}

class GameView : View("Find Pair: enjoy") {
    private val controller: Controller by inject()
    private val buttons: List<MutableList<Button>> = List(controller.gameSize) { mutableListOf() }
    override val root = vbox {
        gridpane {
            for (y in 0 until controller.gameSize)
                row {
                    for (x in 0 until controller.gameSize)
                        buttons[y].add(button(" ") {
                            setPrefSize(CELL_SIZE, CELL_SIZE)
                            setOnAction {
                                controller.makeMove(TurnPlace(y, x), buttons)
                            }
                        })
                }
        }
    }

    companion object {
        const val CELL_SIZE = 100.0
    }
}

class FinalView : View("Find Pair: Final") {
    private val controller: Controller by inject()
    override val root = vbox {
        borderpane { center = label("Congratulations, you won") }
    }
}
