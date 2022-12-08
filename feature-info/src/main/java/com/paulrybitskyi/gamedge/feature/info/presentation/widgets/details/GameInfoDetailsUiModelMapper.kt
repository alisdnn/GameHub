package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.details

import ca.on.hojat.gamenews.shared.domain.games.entities.Game
import ca.on.hojat.gamenews.shared.domain.games.entities.Genre
import ca.on.hojat.gamenews.shared.domain.games.entities.Mode
import ca.on.hojat.gamenews.shared.domain.games.entities.Platform
import ca.on.hojat.gamenews.shared.domain.games.entities.PlayerPerspective
import ca.on.hojat.gamenews.shared.domain.games.entities.Theme
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

internal interface GameInfoDetailsUiModelMapper {
    fun mapToUiModel(game: Game): GameInfoDetailsUiModel?
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class GameInfoDetailsUiModelMapperImpl @Inject constructor() :
    GameInfoDetailsUiModelMapper {

    private companion object {
        private const val TEXT_SEPARATOR = " • "
    }

    override fun mapToUiModel(game: Game): GameInfoDetailsUiModel? {
        @Suppress("ComplexCondition")
        if (game.genres.isEmpty() &&
            game.platforms.isEmpty() &&
            game.modes.isEmpty() &&
            game.playerPerspectives.isEmpty() &&
            game.themes.isEmpty()
        ) {
            return null
        }

        return GameInfoDetailsUiModel(
            genresText = game.genresToText(),
            platformsText = game.platformsToText(),
            modesText = game.modesToText(),
            playerPerspectivesText = game.playerPerspectivesToText(),
            themesText = game.themesToText()
        )
    }

    private fun Game.genresToText(): String? {
        return genres
            .takeIf(List<Genre>::isNotEmpty)
            ?.map(Genre::name)
            ?.joinToString()
    }

    private fun Game.platformsToText(): String? {
        return platforms
            .takeIf(List<Platform>::isNotEmpty)
            ?.map(Platform::name)
            ?.joinToString()
    }

    private fun Game.modesToText(): String? {
        return modes
            .takeIf(List<Mode>::isNotEmpty)
            ?.map(Mode::name)
            ?.joinToString()
    }

    private fun Game.playerPerspectivesToText(): String? {
        return playerPerspectives
            .takeIf(List<PlayerPerspective>::isNotEmpty)
            ?.map(PlayerPerspective::name)
            ?.joinToString()
    }

    private fun Game.themesToText(): String? {
        return themes
            .takeIf(List<Theme>::isNotEmpty)
            ?.map(Theme::name)
            ?.joinToString()
    }

    private fun List<String>.joinToString(): String {
        return joinToString(separator = TEXT_SEPARATOR)
    }
}
