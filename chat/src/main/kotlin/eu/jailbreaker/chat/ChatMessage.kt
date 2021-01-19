package eu.jailbreaker.chat

object ChatMessage {
    const val PREFIX = "§8[§6JailBreaker§8] "

    const val OBJECTIVE_NAME = "dcb_scoreboard"

    const val CHAT_REPEATING = "${PREFIX}§cDu wiederholst dich!"
    const val CHAT_TOO_SHORT = "${PREFIX}§cDeine Nachricht war zu kurz!"
    const val CHAT_COOLDOWN = "${PREFIX}§cBitte warte bevor du wieder eine Nachricht schreibst"

    const val PLAYER_JOIN = "$PREFIX%s §7hat den Server betreten"
    const val PLAYER_LEAVE = "$PREFIX%s §7hat den Server verlassen"
}