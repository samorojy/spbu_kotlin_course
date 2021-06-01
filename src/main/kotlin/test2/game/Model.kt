package test2.game

import javafx.scene.control.Button
import kotlin.random.Random

class Model(gameSize: Int) {
    data class FieldState(val value: Int, var isPressed: Boolean)

    private val gameField: List<MutableList<FieldState>>
    private var lastTurnPlace: TurnPlace? = null

    init {
        val numbersSet = mutableSetOf<Int>()
        while (numbersSet.size != gameSize * gameSize / 2) numbersSet.add(Random.nextInt(0, gameSize * gameSize / 2))
        val numbersList = mutableListOf<Int>()
        repeat(2) { numbersList.addAll(numbersSet) }
        numbersList.shuffle()

        gameField = List(gameSize) { mutableListOf() }
        gameField.forEach {
            for (g in 0 until gameSize) {
                it.add(FieldState(numbersList.last(), false))
                numbersList.removeLast()
            }
        }
    }

    private fun isGameOver(): Boolean {
        gameField.forEach { mutableList ->
            mutableList.forEach {
                if (!it.isPressed) {
                    println(it)
                    return false
                }
            }
        }
        return true
    }

    fun makeTurn(turnPlace: TurnPlace, buttons: List<List<Button>>): Boolean {
        buttons[turnPlace.row][turnPlace.column].text = gameField[turnPlace.row][turnPlace.column].value.toString()
        gameField[turnPlace.row][turnPlace.column].isPressed = true
        if (lastTurnPlace != null) {
            if (gameField[lastTurnPlace!!.row][lastTurnPlace!!.column].value
                != gameField[turnPlace.row][turnPlace.column].value
            ) {
                buttons[turnPlace.row][turnPlace.column].text = " "
                buttons[lastTurnPlace!!.row][lastTurnPlace!!.column].text = " "
            }
            lastTurnPlace = null
        } else lastTurnPlace = turnPlace
        return isGameOver()
    }
}
