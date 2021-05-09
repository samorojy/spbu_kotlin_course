@file:Suppress("NoWildcardImports", "WildcardImport")

package homework8

import tornadofx.App
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import tornadofx.View

class TicTacToe : App(StartView::class, GameView.GameStyle::class)

class StartView : View("Tic-Tac-Toe: Menu") {
    companion object {
        const val CELL_SIZE = 100.0
    }

    override val root = vbox {
        setPrefSize(Controller.GAME_FIELD_SIZE * CELL_SIZE, Controller.GAME_FIELD_SIZE * CELL_SIZE)

        menubar {
            menu("Settings") {
                menu("Game mode") {
                    item("Player vs Players").action { Controller.changeGameMode(GameMode.PlayerVsPlayer) }
                    separator()
                    menu("Player vs Computer") {
                        item("Easy Mode").action { Controller.changeGameMode(GameMode.PlayerVsComputerEasy) }
                        separator()
                        item("Hard Mode").action { Controller.changeGameMode(GameMode.PlayerVsComputerHard) }
                    }
                }
            }
        }

        button("Start game") {
            useMaxWidth = true
            setOnAction { Controller.startGame() }
        }
    }
}

class FinishView : View("Tic-Tac-Toe: Game Over") {
    override val root = vbox {
        borderpane {
            useMaxWidth = true
            center = label("Game Over!") {
                style() {
                    fontSize = 30.px
                    fontFamily = "Montserrat"
                    fontWeight = FontWeight.EXTRA_BOLD
                    borderColor += box(
                        top = Color.RED,
                        right = Color.DARKGREEN,
                        left = Color.ORANGE,
                        bottom = Color.PURPLE
                    )
                }
            }
        }
    }
}

class GameView : View("Tic-Tac-Toe: Game") {
    companion object {
        const val CELL_SIZE = 100.0
    }

    override val root = vbox {
        setPrefSize(Controller.GAME_FIELD_SIZE * CELL_SIZE, Controller.GAME_FIELD_SIZE * CELL_SIZE)
        gridpane {
            for (y in 0 until Controller.GAME_FIELD_SIZE)
                row {
                    for (x in 0 until Controller.GAME_FIELD_SIZE)
                        button(" ") {
                            setPrefSize(CELL_SIZE, CELL_SIZE)
                            setOnAction {
                                if (text == " ") {
                                    text = Controller.updateCell(x, y).toString()
                                }
                            }
                        }
                }
        }
    }

    class GameStyle : Stylesheet() {
        init {
            button {
                fontSize = 30.px
                fontFamily = "Helvetica"
                fontWeight = FontWeight.EXTRA_BOLD
                borderColor += box(
                    top = Color.RED,
                    right = Color.DARKGREEN,
                    left = Color.ORANGE,
                    bottom = Color.PURPLE
                )
            }
        }
    }
}
