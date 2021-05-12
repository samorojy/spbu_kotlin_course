@file:Suppress("NoWildcardImports", "WildcardImport")

package homework8

import javafx.scene.control.Button
import tornadofx.App
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import tornadofx.View

class TicTacToe : App(TicTacToeViews.StartView::class, TicTacToeViews.GameView.GameStyle::class)

class TicTacToeViews {
    val controller = Controller()

    inner class StartView : View("Tic-Tac-Toe: Menu") {
        override val root = vbox {
            setPrefSize(MENU_WIDTH * CELL_SIZE, MENU_HEIGHT * CELL_SIZE)

            menubar {
                menu("Settings") {
                    menu("Game mode") {
                        item("Player vs Players").action { controller.changeGameMode(GameMode.PlayerVsPlayer) }
                        separator()
                        menu("Player vs Computer") {
                            item("Easy Mode").action { controller.changeGameMode(GameMode.PlayerVsComputerEasy) }
                            separator()
                            item("Hard Mode").action { controller.changeGameMode(GameMode.PlayerVsComputerHard) }
                        }
                    }
                }
            }

            button("Start game") {
                useMaxWidth = true
                setOnAction { controller.startGame() }
            }
        }

        companion object {
            const val CELL_SIZE = 100.0
            const val MENU_HEIGHT = 1.5
            const val MENU_WIDTH = 3
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

    class GameView(private val controller: Controller) : View("Tic-Tac-Toe: Game") {

        private val gameSize = controller.getGameSize()
        private val buttons = Array(gameSize) { Array<Button?>(gameSize) { null } }
        override val root = vbox {
            setPrefSize(gameSize * CELL_SIZE, gameSize * CELL_SIZE)
            gridpane {
                for (y in 0 until gameSize)
                    row {
                        for (x in 0 until gameSize)
                            buttons[y][x] = button(" ") {
                                setPrefSize(CELL_SIZE, CELL_SIZE)
                                setOnAction {
                                    if (text == " ") {
                                        text = controller.makeTurn(x, y)
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

        companion object {
            const val CELL_SIZE = 100.0
        }
    }
}