package ca.hojat.gamehub.feature_info.presentation.widgets.screenshots

import ca.hojat.gamehub.core.factories.IgdbImageSize
import ca.hojat.gamehub.core.factories.IgdbImageUrlFactory
import ca.hojat.gamehub.core.domain.entities.Image
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

abstract class InfoScreenShotUiModelMapper {
    internal abstract fun mapToUiModel(image: Image): InfoScreenShotUiModel?

    internal fun mapToUiModels(
        images: List<Image>,
    ): List<InfoScreenShotUiModel> {
        if (images.isEmpty()) return emptyList()

        return images.mapNotNull(::mapToUiModel)
    }

}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class InfoScreenShotUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : InfoScreenShotUiModelMapper() {

    override fun mapToUiModel(image: Image): InfoScreenShotUiModel? {
        val screenshotUrl = igdbImageUrlFactory.createUrl(
            image,
            IgdbImageUrlFactory.Config(IgdbImageSize.MEDIUM_SCREENSHOT),
        ) ?: return null

        return InfoScreenShotUiModel(
            id = image.id,
            url = screenshotUrl,
        )
    }
}
