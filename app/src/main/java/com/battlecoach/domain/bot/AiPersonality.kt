package com.battlecoach.domain.bot

import com.battlecoach.domain.engine.EngineLevel

sealed class AiPersonality(
    val name: String,
    val title: String,
    val baseElo: Int,
    val responses: Map<GameSituation, List<String>>,
    val voicePackId: String
) {
    class SungJinWoo : AiPersonality(
        name = "Sung Jin-Woo",
        title = "Shadow Monarch",
        baseElo = 2500,
        voicePackId = "sung_jin_woo_v1",
        responses = mapOf(
            GameSituation.GAME_START to listOf(
                "Let's see if you're worthy of joining my shadow army.",
                "Show me your strength, hunter."
            ),
            GameSituation.PLAYER_GOOD_MOVE to listOf(
                "Arise! That was a worthy move.",
                "You might be strong enough to be my shadow."
            ),
            GameSituation.PLAYER_BLUNDER to listOf(
                "Is that all you've got? Even E-rank hunters do better.",
                "You need more training in my dungeon."
            ),
            GameSituation.CHECKMATE_WIN to listOf(
                "This is the power of the Shadow Monarch.",
                "Return to the shadows and grow stronger."
            ),
            GameSituation.CHECKMATE_LOSS to listOf(
                "You... you might be stronger than the Architect himself.",
                "I haven't felt this excited since fighting the Demon King."
            )
        )
    )

    class ChaeHaeIn : AiPersonality(
        name = "Chae Hae-In",
        title = "White Tiger Guild Master",
        baseElo = 2300,
        voicePackId = "chae_hae_in_v1",
        responses = mapOf(
            GameSituation.GAME_START to listOf(
                "I sense your potential. Show me what you've got.",
                "Let's see if you're as strong as Jin-Woo says."
            ),
            GameSituation.PLAYER_GOOD_MOVE to listOf(
                "Impressive! Your aura is getting stronger.",
                "That's the spirit of an S-rank hunter!"
            ),
            GameSituation.PLAYER_BLUNDER to listOf(
                "Focus! A real dungeon won't be this forgiving.",
                "You're better than this. I've seen it."
            )
        )
    )

    class GoGunHee : AiPersonality(
        name = "Go Gun-Hee",
        title = "Korean Hunter Association Chairman",
        baseElo = 2400,
        voicePackId = "go_gun_hee_v1",
        responses = mapOf(
            GameSituation.GAME_START to listOf(
                "Young hunter, show me your resolve.",
                "Let this old man test your strength."
            ),
            GameSituation.PLAYER_GOOD_MOVE to listOf(
                "Ah, you remind me of my younger days.",
                "Korea's future is bright with hunters like you."
            )
        )
    )

    class ThomasAndre : AiPersonality(
        name = "Thomas Andre",
        title = "Scavenger Guild Master",
        baseElo = 2600,
        voicePackId = "thomas_andre_v1",
        responses = mapOf(
            GameSituation.GAME_START to listOf(
                "Let's see what Korean hunters are made of!",
                "Don't disappoint me, challenger."
            ),
            GameSituation.BOT_ADVANTAGE to listOf(
                "Is this all America's rivals can offer?",
                "You're a hundred years too early to challenge me!"
            )
        )
    )

    // Add more personalities...
}

enum class GameSituation {
    GAME_START,
    PLAYER_GOOD_MOVE,
    PLAYER_BLUNDER,
    PLAYER_ADVANTAGE,
    BOT_ADVANTAGE,
    CHECKMATE_WIN,
    CHECKMATE_LOSS,
    DRAW_OFFER,
    TIME_PRESSURE
} 