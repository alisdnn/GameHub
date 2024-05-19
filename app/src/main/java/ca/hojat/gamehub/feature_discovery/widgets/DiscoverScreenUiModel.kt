package ca.hojat.gamehub.feature_discovery.widgets

import androidx.compose.runtime.Immutable

@Immutable
internal data class DiscoverScreenUiModel(
    val id: Int,
    val categoryName: String,
    val title: String,
    val isProgressBarVisible: Boolean,
    val items: List<DiscoverScreenItemData>,
)

internal fun List<DiscoverScreenUiModel>.toSuccessState(
    items: List<List<DiscoverScreenItemData>>,
): List<DiscoverScreenUiModel> {
    return mapIndexed { index, itemModel ->
        itemModel.copy(items = items[index])
    }
}

internal fun List<DiscoverScreenUiModel>.showProgressBar(): List<DiscoverScreenUiModel> {
    return map { itemModel -> itemModel.copy(isProgressBarVisible = true) }
}

internal fun List<DiscoverScreenUiModel>.hideProgressBar(): List<DiscoverScreenUiModel> {
    return map { itemModel -> itemModel.copy(isProgressBarVisible = false) }
}
