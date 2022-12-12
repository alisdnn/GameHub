package ca.on.hojat.gamenews.shared.ui.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.on.hojat.gamenews.shared.R
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State
import ca.on.hojat.gamenews.shared.ui.images.defaultImageRequest
import ca.on.hojat.gamenews.shared.ui.images.secondaryImage
import ca.on.hojat.gamenews.shared.ui.theme.GamedgeTheme

val DefaultCoverWidth = 112.dp
val DefaultCoverHeight = 153.dp

@Composable
fun GameCover(
    title: String?,
    imageUrl: String?,
    modifier: Modifier = Modifier,
    hasRoundedShape: Boolean = true,
    onCoverClicked: (() -> Unit)? = null,
) {
    val cardModifier = modifier.size(width = DefaultCoverWidth, height = DefaultCoverHeight)
    val shape = if (hasRoundedShape) GamedgeTheme.shapes.medium else RectangleShape
    val backgroundColor = Color.Transparent
    val content: @Composable () -> Unit = {
        Box {
            var imageState by remember { mutableStateOf<State>(State.Empty) }
            // Not using derivedStateOf here because rememberSaveable does not support derivedStateOf?
            // https://stackoverflow.com/questions/71986944/custom-saver-remembersaveable-using-derivedstateof
            val shouldDisplayTitle = rememberSaveable(title, imageState) {
                (title != null) &&
                        (imageState !is State.Success)
            }

            AsyncImage(
                model = defaultImageRequest(imageUrl) {
                    secondaryImage(R.drawable.game_cover_placeholder)
                },
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                onState = { state ->
                    imageState = state
                },
                contentScale = ContentScale.Crop,
            )

            if (shouldDisplayTitle) {
                Text(
                    text = checkNotNull(title),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = GamedgeTheme.spaces.spacing_4_0),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = GamedgeTheme.typography.caption,
                )
            }
        }
    }

    if (onCoverClicked != null) {
        GameNewsCard(
            onClick = onCoverClicked,
            modifier = cardModifier,
            shape = shape,
            backgroundColor = backgroundColor,
            content = content,
        )
    } else {
        GameNewsCard(
            modifier = cardModifier,
            shape = shape,
            backgroundColor = backgroundColor,
            content = content,
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GameCoverWithTitlePreview() {
    GamedgeTheme {
        GameCover(
            title = "Ghost of Tsushima: Director's Cut",
            imageUrl = null,
            onCoverClicked = {},
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GameCoverWithoutTitlePreview() {
    GamedgeTheme {
        GameCover(
            title = null,
            imageUrl = null,
            onCoverClicked = {},
        )
    }
}
