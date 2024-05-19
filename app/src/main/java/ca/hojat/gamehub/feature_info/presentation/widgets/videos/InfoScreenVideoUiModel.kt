package ca.hojat.gamehub.feature_info.presentation.widgets.videos

import androidx.compose.runtime.Immutable

@Immutable
internal data class InfoScreenVideoUiModel(
    val id: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val title: String,
)
