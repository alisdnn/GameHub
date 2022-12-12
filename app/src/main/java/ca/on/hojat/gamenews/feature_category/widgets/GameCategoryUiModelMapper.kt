package ca.on.hojat.gamenews.feature_category.widgets

import ca.on.hojat.gamenews.shared.core.factories.IgdbImageSize
import ca.on.hojat.gamenews.shared.core.factories.IgdbImageUrlFactory
import ca.on.hojat.gamenews.shared.domain.games.entities.Game
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

internal interface GameCategoryUiModelMapper {
    fun mapToUiModel(game: Game): GameCategoryUiModel
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class GameCategoryUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : GameCategoryUiModelMapper {

    override fun mapToUiModel(game: Game): GameCategoryUiModel {
        return GameCategoryUiModel(
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

internal fun GameCategoryUiModelMapper.mapToUiModels(
    games: List<Game>,
): List<GameCategoryUiModel> {
    return games.map(::mapToUiModel)
}
