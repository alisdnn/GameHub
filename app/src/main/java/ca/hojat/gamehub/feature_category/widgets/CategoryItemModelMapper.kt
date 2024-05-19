package ca.hojat.gamehub.feature_category.widgets

import ca.hojat.gamehub.core.factories.IgdbImageSize
import ca.hojat.gamehub.core.factories.IgdbImageUrlFactory
import ca.hojat.gamehub.core.domain.entities.Game
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

/**
 * This mapper converts Domain entities to stuff we can use
 * in the UI layer of "category" feature.
 */
abstract class CategoryItemModelMapper {

    internal abstract fun mapToUiModel(game: Game): CategoryUiModel

    internal fun mapToUiModels(
        games: List<Game>,
    ): List<CategoryUiModel> {
        return games.map(::mapToUiModel)
    }
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class CategoryItemModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : CategoryItemModelMapper() {
    override fun mapToUiModel(game: Game): CategoryUiModel {
        return CategoryUiModel(
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
