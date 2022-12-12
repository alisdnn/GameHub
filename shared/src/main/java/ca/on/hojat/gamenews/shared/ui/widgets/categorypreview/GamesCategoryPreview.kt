package ca.on.hojat.gamenews.shared.ui.widgets.categorypreview

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import ca.on.hojat.gamenews.shared.R
import ca.on.hojat.gamenews.shared.ui.theme.GameNewsTheme
import ca.on.hojat.gamenews.shared.ui.widgets.GameCover
import ca.on.hojat.gamenews.shared.ui.widgets.GameNewsCard
import ca.on.hojat.gamenews.shared.ui.widgets.GamedgeProgressIndicator
import ca.on.hojat.gamenews.shared.ui.widgets.Info

@Composable
fun GamesCategoryPreview(
    title: String,
    isProgressBarVisible: Boolean,
    games: List<GamesCategoryPreviewItemUiModel>,
    onCategoryGameClicked: (GamesCategoryPreviewItemUiModel) -> Unit,
    topBarMargin: Dp = GameNewsTheme.spaces.spacing_2_0,
    isMoreButtonVisible: Boolean = true,
    onCategoryMoreButtonClicked: (() -> Unit)? = null,
) {
    GameNewsCard(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val titleRefMargin = GameNewsTheme.spaces.spacing_3_5
            val progressBarMarginEnd = GameNewsTheme.spaces.spacing_1_5
            val moreBtnHorizontalMargin = GameNewsTheme.spaces.spacing_1_5
            val refs = createRefs()
            val (titleRef, progressBarRef, moreBtnRef, itemsListRef, infoRef) = refs
            val topBarBarrier =
                createBottomBarrier(titleRef, progressBarRef, moreBtnRef, margin = topBarMargin)

            Title(
                title = title,
                modifier = Modifier.constrainAs(titleRef) {
                    width = Dimension.fillToConstraints
                    top.linkTo(parent.top, titleRefMargin)
                    start.linkTo(parent.start, titleRefMargin)
                    end.linkTo(progressBarRef.start, titleRefMargin)
                },
            )

            if (isProgressBarVisible) {
                ProgressBar(
                    modifier = Modifier.constrainAs(progressBarRef) {
                        centerVerticallyTo(moreBtnRef)
                        end.linkTo(moreBtnRef.start, progressBarMarginEnd)
                    },
                )
            }

            if (isMoreButtonVisible) {
                MoreButton(
                    modifier = Modifier.constrainAs(moreBtnRef) {
                        centerVerticallyTo(titleRef)
                        end.linkTo(parent.end, moreBtnHorizontalMargin)
                    },
                    onCategoryMoreButtonClicked = onCategoryMoreButtonClicked,
                )
            }

            Content(
                games = games,
                topBarBarrier = topBarBarrier,
                infoRef = infoRef,
                itemsListRef = itemsListRef,
                onCategoryGameClicked = onCategoryGameClicked,
            )
        }
    }
}

@Composable
private fun Title(
    title: String,
    modifier: Modifier,
) {
    Text(
        text = title,
        modifier = modifier,
        color = GameNewsTheme.colors.onPrimary,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = GameNewsTheme.typography.h6,
    )
}

@Composable
private fun ProgressBar(modifier: Modifier) {
    GamedgeProgressIndicator(
        modifier = modifier.size(16.dp),
        strokeWidth = 2.dp,
    )
}

@Composable
private fun MoreButton(
    modifier: Modifier,
    onCategoryMoreButtonClicked: (() -> Unit)?,
) {
    val clickableModifier = if (onCategoryMoreButtonClicked != null) {
        Modifier.clickable(onClick = onCategoryMoreButtonClicked)
    } else {
        Modifier
    }

    Text(
        text = stringResource(R.string.games_category_preview_more_button_text).uppercase(),
        modifier = modifier
            .then(clickableModifier)
            .padding(GameNewsTheme.spaces.spacing_2_0),
        color = GameNewsTheme.colors.secondary,
        style = GameNewsTheme.typography.button,
    )
}

@Composable
private fun ConstraintLayoutScope.Content(
    games: List<GamesCategoryPreviewItemUiModel>,
    topBarBarrier: ConstraintLayoutBaseScope.HorizontalAnchor,
    infoRef: ConstrainedLayoutReference,
    itemsListRef: ConstrainedLayoutReference,
    onCategoryGameClicked: (GamesCategoryPreviewItemUiModel) -> Unit,
) {
    if (games.isEmpty()) {
        val infoBottomMargin = GameNewsTheme.spaces.spacing_7_0
        val infoHorizontalMargin = GameNewsTheme.spaces.spacing_7_5

        EmptyState(
            modifier = Modifier.constrainAs(infoRef) {
                top.linkTo(topBarBarrier)
                bottom.linkTo(parent.bottom, infoBottomMargin)
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    startMargin = infoHorizontalMargin,
                    endMargin = infoHorizontalMargin
                )
            },
        )
    } else {
        SuccessState(
            games = games,
            modifier = Modifier.constrainAs(itemsListRef) {
                width = Dimension.fillToConstraints
                top.linkTo(topBarBarrier)
                bottom.linkTo(parent.bottom)
                centerHorizontallyTo(parent)
            },
            onCategoryGameClicked = onCategoryGameClicked,
        )
    }
}

@Composable
private fun EmptyState(modifier: Modifier) {
    Info(
        icon = painterResource(R.drawable.google_controller),
        title = stringResource(R.string.games_category_preview_info_view_title),
        modifier = modifier,
        iconSize = 80.dp,
    )
}

@Composable
private fun SuccessState(
    games: List<GamesCategoryPreviewItemUiModel>,
    modifier: Modifier,
    onCategoryGameClicked: (GamesCategoryPreviewItemUiModel) -> Unit,
) {
    val padding = GameNewsTheme.spaces.spacing_3_5

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(
            start = padding,
            end = padding,
            bottom = padding,
        ),
        horizontalArrangement = Arrangement.spacedBy(GameNewsTheme.spaces.spacing_1_5),
    ) {
        items(items = games, key = GamesCategoryPreviewItemUiModel::id) { item ->
            GameCover(
                title = item.title,
                imageUrl = item.coverUrl,
                onCoverClicked = { onCategoryGameClicked(item) },
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GamesCategoryPreviewSuccessStateWithMoreButtonPreview() {
    GameNewsTheme {
        GamesCategoryPreview(
            title = "Popular",
            isProgressBarVisible = false,
            games = listOf(
                GamesCategoryPreviewItemUiModel(
                    id = 1,
                    title = "Ghost of Tsushima: Director's Cut",
                    coverUrl = null,
                ),
                GamesCategoryPreviewItemUiModel(
                    id = 2,
                    title = "Outer Wilds: Echoes of the Eye",
                    coverUrl = null,
                ),
                GamesCategoryPreviewItemUiModel(
                    id = 3,
                    title = "Kena: Bridge of Spirits",
                    coverUrl = null,
                ),
                GamesCategoryPreviewItemUiModel(
                    id = 4,
                    title = "Forza Horizon 5",
                    coverUrl = null,
                ),
            ),
            onCategoryMoreButtonClicked = {},
            onCategoryGameClicked = {},
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GamesCategoryPreviewSuccessStateWithoutMoreButtonPreview() {
    GameNewsTheme {
        GamesCategoryPreview(
            title = "Popular",
            isProgressBarVisible = false,
            isMoreButtonVisible = false,
            games = listOf(
                GamesCategoryPreviewItemUiModel(
                    id = 1,
                    title = "Ghost of Tsushima: Director's Cut",
                    coverUrl = null,
                ),
                GamesCategoryPreviewItemUiModel(
                    id = 2,
                    title = "Outer Wilds: Echoes of the Eye",
                    coverUrl = null,
                ),
                GamesCategoryPreviewItemUiModel(
                    id = 3,
                    title = "Kena: Bridge of Spirits",
                    coverUrl = null,
                ),
                GamesCategoryPreviewItemUiModel(
                    id = 4,
                    title = "Forza Horizon 5",
                    coverUrl = null,
                ),
            ),
            onCategoryMoreButtonClicked = {},
            onCategoryGameClicked = {},
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GamesCategoryPreviewEmptyStatePreview() {
    GameNewsTheme {
        GamesCategoryPreview(
            title = "Popular",
            isProgressBarVisible = false,
            games = emptyList(),
            onCategoryMoreButtonClicked = {},
            onCategoryGameClicked = {},
        )
    }
}
