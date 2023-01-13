package ca.on.hojat.gamenews.feature_info.presentation.widgets.companies

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import ca.on.hojat.gamenews.R
import ca.on.hojat.gamenews.common_ui.widgets.GameNewsCard
import ca.on.hojat.gamenews.core.common_ui.images.secondaryImage
import ca.on.hojat.gamenews.core.extensions.centerX
import ca.on.hojat.gamenews.core.extensions.centerY
import ca.on.hojat.gamenews.core.extensions.hasTransparentPixels
import ca.on.hojat.gamenews.feature_info.presentation.widgets.utils.GameInfoSectionWithInnerList
import ca.on.hojat.gamenews.core.common_ui.images.defaultImageRequest
import ca.on.hojat.gamenews.core.common_ui.theme.GameNewsTheme
import coil.compose.rememberAsyncImagePainter
import coil.size.Size
import coil.transform.Transformation
import kotlin.math.roundToInt

private val LogoMaxWidth = 268.dp
private val LogoMaxHeight = 150.dp
private const val COLOR_ALPHA_MAX = 255

@Composable
internal fun GameInfoCompanies(
    companies: List<GameInfoCompanyUiModel>,
    onCompanyClicked: (GameInfoCompanyUiModel) -> Unit,
) {
    GameInfoSectionWithInnerList(title = stringResource(R.string.game_info_companies_title)) {
        items(items = companies, key = GameInfoCompanyUiModel::id) { company ->
            Company(
                company = company,
                onCompanyClicked = { onCompanyClicked(company) },
            )
        }
    }
}

@Composable
private fun Company(
    company: GameInfoCompanyUiModel,
    onCompanyClicked: () -> Unit,
) {
    val logoImageSize = rememberLogoImageSize(company)
    val logoContainerSizeInDp = DpSize(
        width = with(LocalDensity.current) { logoImageSize.width.toDp() },
        height = LogoMaxHeight,
    )

    GameNewsCard(
        onClick = onCompanyClicked,
        shape = GameNewsTheme.shapes.medium,
        backgroundColor = Color.Transparent,
    ) {
        Column {
            CompanyLogoImage(
                logoImageUrl = company.logoUrl,
                logoContainerSize = logoContainerSizeInDp,
                logoImageSize = logoImageSize,
            )

            CompanyDetails(
                name = company.name,
                roles = company.roles,
                containerWidth = logoContainerSizeInDp.width,
            )
        }
    }
}

@Composable
private fun rememberLogoImageSize(company: GameInfoCompanyUiModel): IntSize {
    val density = LocalDensity.current

    return remember(company.logoWidth, company.logoHeight) {
        val logoMaxWidthInPx = with(density) { LogoMaxWidth.roundToPx() }
        val logoMaxHeightInPx = with(density) { LogoMaxHeight.roundToPx() }

        if (!company.hasLogoSize) {
            return@remember IntSize(logoMaxWidthInPx, logoMaxHeightInPx)
        }

        val logoWidth = checkNotNull(company.logoWidth)
        val logoHeight = checkNotNull(company.logoHeight)
        val aspectRatio = (logoWidth.toFloat() / logoHeight.toFloat())
        val adjustedWidth = (aspectRatio * logoMaxHeightInPx).roundToInt()

        if (adjustedWidth <= logoMaxWidthInPx) {
            return@remember IntSize(adjustedWidth, logoMaxHeightInPx)
        }

        val widthFraction = (logoMaxWidthInPx.toFloat() / adjustedWidth.toFloat())
        val finalHeight = (widthFraction * logoMaxHeightInPx).roundToInt()

        return@remember IntSize(logoMaxWidthInPx, finalHeight)
    }
}

@Composable
private fun CompanyLogoImage(
    logoImageUrl: String?,
    logoContainerSize: DpSize,
    logoImageSize: IntSize,
) {
    val density = LocalDensity.current
    val logoContainerWidthInPx = with(density) { logoContainerSize.width.roundToPx() }
    val logoContainerHeightInPx = with(density) { logoContainerSize.height.roundToPx() }
    val painter = rememberAsyncImagePainter(
        model = defaultImageRequest(logoImageUrl) {
            secondaryImage(R.drawable.game_landscape_placeholder)
            size(logoImageSize.width, logoImageSize.height)
            transformations(
                LogoImageTransformation(
                    logoContainerWidth = logoContainerWidthInPx,
                    logoContainerHeight = logoContainerHeightInPx,
                )
            )
        },
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.size(
            width = logoContainerSize.width,
            height = logoContainerSize.height,
        ),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun CompanyDetails(
    name: String,
    roles: String,
    containerWidth: Dp,
) {
    Surface(
        modifier = Modifier.width(containerWidth),
        color = GameNewsTheme.colors.primaryVariant,
        contentColor = GameNewsTheme.colors.onSurface,
    ) {
        Column(modifier = Modifier.padding(GameNewsTheme.spaces.spacing_2_5)) {
            Text(
                text = name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = GameNewsTheme.typography.caption,
            )
            Text(
                text = roles,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = GameNewsTheme.typography.caption,
            )
        }
    }
}

private class LogoImageTransformation(
    private val logoContainerWidth: Int,
    private val logoContainerHeight: Int,
) : Transformation {

    private companion object {
        private const val FILL_COLOR_CALCULATION_PIXEL_OFFSET = 10
        private const val TARGET_BITMAP_SCALE_FACTOR = 0.85f
    }

    override val cacheKey = "logo: w - $logoContainerWidth, h - $logoContainerHeight"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val targetBitmap = Bitmap
            .createBitmap(logoContainerWidth, logoContainerHeight, input.config)
            .apply { eraseColor(input.calculateFillColor()) }

        val targetCenterX = targetBitmap.centerX
        val targetCenterY = targetBitmap.centerY
        val sourceOffsetLeft = (targetCenterX - input.centerX)
        val sourceOffsetTop = (targetCenterY - input.centerY)

        return Canvas(targetBitmap)
            .apply {
                scale(
                    TARGET_BITMAP_SCALE_FACTOR,
                    TARGET_BITMAP_SCALE_FACTOR,
                    targetCenterX,
                    targetCenterY
                )

                drawBitmap(input, sourceOffsetLeft, sourceOffsetTop, null)
            }
            .also { input.recycle() }
            .let { targetBitmap }
    }

    private fun Bitmap.calculateFillColor(): Int {
        if (hasTransparentPixels()) return android.graphics.Color.WHITE

        var pixelColor: Int

        for (x in FILL_COLOR_CALCULATION_PIXEL_OFFSET..centerX.toInt()) {
            for (y in FILL_COLOR_CALCULATION_PIXEL_OFFSET..centerY.toInt()) {
                pixelColor = getPixel(x, y)

                if (android.graphics.Color.alpha(pixelColor) == COLOR_ALPHA_MAX) return pixelColor
            }
        }

        return android.graphics.Color.WHITE
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GameInfoCompaniesPreview() {
    GameNewsTheme {
        GameInfoCompanies(
            companies = listOf(
                GameInfoCompanyUiModel(
                    id = 1,
                    logoUrl = null,
                    logoWidth = 1400,
                    logoHeight = 400,
                    websiteUrl = "",
                    name = "FromSoftware",
                    roles = "Main Developer",
                ),
                GameInfoCompanyUiModel(
                    id = 2,
                    logoUrl = null,
                    logoWidth = 500,
                    logoHeight = 400,
                    websiteUrl = "",
                    name = "Bandai Namco Entertainment",
                    roles = "Publisher",
                ),
            ),
            onCompanyClicked = {},
        )
    }
}
