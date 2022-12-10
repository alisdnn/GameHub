package com.paulrybitskyi.gamedge.feature_discovery.mapping

import ca.on.hojat.gamenews.shared.core.factories.IgdbImageSize
import ca.on.hojat.gamenews.shared.core.factories.IgdbImageUrlFactory
import ca.on.hojat.gamenews.shared.domain.games.entities.Game
import com.paulrybitskyi.gamedge.feature_discovery.widgets.GamesDiscoveryItemGameUiModel
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

internal interface GamesDiscoveryItemGameUiModelMapper {
    fun mapToUiModel(game: Game): GamesDiscoveryItemGameUiModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class GamesDiscoveryItemGameUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory
) : GamesDiscoveryItemGameUiModelMapper {

    override fun mapToUiModel(game: Game): GamesDiscoveryItemGameUiModel {
        return GamesDiscoveryItemGameUiModel(
            id = game.id,
            title = game.name,
            coverUrl = game.cover?.let { cover ->
                igdbImageUrlFactory.createUrl(
                    cover,
                    IgdbImageUrlFactory.Config(IgdbImageSize.BIG_COVER)
                )
            }
        )
    }
}

internal fun GamesDiscoveryItemGameUiModelMapper.mapToUiModels(
    games: List<Game>,
): List<GamesDiscoveryItemGameUiModel> {
    return games.map(::mapToUiModel)
}
