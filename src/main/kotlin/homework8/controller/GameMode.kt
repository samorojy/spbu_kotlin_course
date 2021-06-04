package homework8.controller

enum class GameMode(val presentableName: String) {
    PLAYER_VS_PLAYER_LOCAL("Playing versus Player on 1 PC"),
    PLAYER_VS_PLAYER_ONLINE("Playing versus Player online"),
    PLAYER_VS_COMPUTER_EASY("Playing versus easy bot"),
    PLAYER_VS_COMPUTER_HARD("Playing versus hard bot")
}
