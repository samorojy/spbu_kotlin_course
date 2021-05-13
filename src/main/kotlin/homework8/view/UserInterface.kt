@file:Suppress("NoWildcardImports", "WildcardImport")

package homework8

import homework8.controller.Controller
import homework8.controller.GameMode
import homework8.controller.TurnPlace
import javafx.scene.control.Button
import tornadofx.App
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*
import tornadofx.View

class TicTacToe : App(StartView::class, GameView.GameStyle::class)

class StartView : View("Tic-Tac-Toe: Menu") {
    private val controller: Controller by inject()
    private val gameSize = controller.getGameSize()
    override val root = vbox {
        setPrefSize(gameSize * CELL_SIZE, gameSize * CELL_SIZE)

        menubar {
            menu("Settings") {
                menu("Game mode") {
                    menu("Player vs Player") {
                        item("Local").action { controller.changeGameMode(GameMode.PlayerVsPlayerLocal) }
                        separator()
                        item("Online").action { controller.changeGameMode(GameMode.PlayerVsPlayerOnline) }
                    }
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
    private val controller: Controller by inject()
    private val gameSize = controller.getGameSize()
    private val buttons: List<MutableList<Button>> = List(gameSize) { mutableListOf() }
    override val root = vbox {
        setPrefSize(gameSize * CELL_SIZE, gameSize * CELL_SIZE)
        gridpane {
            for (y in 0 until gameSize)
                row {
                    for (x in 0 until gameSize)
                        buttons[y].add(button(" ") {
                            setPrefSize(CELL_SIZE, CELL_SIZE)
                            setOnAction {
                                if (text == " ") {
                                    controller.makeTurn(TurnPlace(y, x), buttons)
                                }
                            }
                        })
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
