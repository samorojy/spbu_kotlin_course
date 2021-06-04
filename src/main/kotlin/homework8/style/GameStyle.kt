package homework8.style

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class GameStyle : Stylesheet() {
    init {
        Companion.gameViewStyle {
            label {
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
        Companion.menuStyle {
            label {
                fontSize = 13.5.px
                fontFamily = "Helvetica"
                fontWeight = FontWeight.MEDIUM
                borderColor += box(
                    top = Color.GAINSBORO,
                    right = Color.GAINSBORO,
                    left = Color.GAINSBORO,
                    bottom = Color.GAINSBORO
                )
            }
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
        val gameViewStyle by cssclass()
        val menuStyle by cssclass()
    }
}
