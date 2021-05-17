package homework8.view

import homework8.controller.Controller
import homework8.controller.GameMode
import homework8.controller.TurnAuthor
import homework8.controller.TurnPlace
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.ToggleGroup
import tornadofx.App
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.separator
import tornadofx.togglebutton
import tornadofx.View
import tornadofx.action
import tornadofx.borderpane
import tornadofx.box
import tornadofx.button
import tornadofx.gridpane
import tornadofx.hbox
import tornadofx.item
import tornadofx.label
import tornadofx.menu
import tornadofx.menubar
import tornadofx.px
import tornadofx.row
import tornadofx.style
import tornadofx.useMaxWidth
import tornadofx.vbox

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
                        item("Local").action { controller.changeGameMode(GameMode.PLAYER_VS_PLAYER_LOCAL) }
                        separator()
                        item("Online").action { controller.changeGameMode(GameMode.PLAYER_VS_PLAYER_ONLINE) }
                    }
                    separator()
                    menu("Player vs Computer") {
                        item("Easy Mode").action { controller.changeGameMode(GameMode.PLAYER_VS_COMPUTER_EASY) }
                        separator()
                        item("Hard Mode").action { controller.changeGameMode(GameMode.PLAYER_VS_COMPUTER_HARD) }
                    }
                }
                item("[NON-STABLE] Game size \n Working only at first start") {
                    hbox {
                        val toggleGroup = ToggleGroup()
                        @Suppress("MagicNumber")
                        togglebutton("3", toggleGroup).action { controller.changeGameSize(3) }
                        @Suppress("MagicNumber")
                        togglebutton("5", toggleGroup).action { controller.changeGameSize(5) }
                        @Suppress("MagicNumber")
                        togglebutton("7", toggleGroup).action { controller.changeGameSize(7) }
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
    private val controller: Controller by inject()
    override val root = vbox {
        borderpane {
            useMaxWidth = true
            center = label("Game Over!") {
                style() {
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
        button("Back to menu") {
            useMaxWidth = true
            setOnAction { controller.restartGame() }
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
                                controller.makeTurn(TurnPlace(y, x), buttons, TurnAuthor.CLIENT)
                            }
                        })
                }
        }
        runAsync { controller.getCurrentState(buttons) }
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
