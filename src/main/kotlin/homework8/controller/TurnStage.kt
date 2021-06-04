package homework8.controller

enum class TurnStage(val sign: String) {
    DRAW("X0"),
    WIN_X("X"),
    WIN_0("0"),
    NO_WINNER_YET_X("X"),
    NO_WINNER_YET_0("0")
}
