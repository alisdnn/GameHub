package ca.hojat.gamehub.feature_category.widgets

import androidx.compose.runtime.Immutable
import ca.hojat.gamehub.common_ui.widgets.FiniteUiState

@Immutable
internal data class CategoryUiState(
    val isLoading: Boolean,
    val title: String,
    val items: List<CategoryUiModel>,
) {
    internal val finiteUiState: FiniteUiState
        get() = when {
            isInEmptyState -> FiniteUiState.EMPTY
            isInLoadingState -> FiniteUiState.LOADING
            isInSuccessState -> FiniteUiState.SUCCESS
            else -> error("Unknown games category UI state.")
        }

    private val isInEmptyState: Boolean
        get() = (!isLoading && items.isEmpty())

    private val isInLoadingState: Boolean
        get() = (isLoading && items.isEmpty())

    private val isInSuccessState: Boolean
        get() = items.isNotEmpty()

    internal val isRefreshing: Boolean
        get() = (isLoading && items.isNotEmpty())

    internal fun enableLoading(): CategoryUiState {
        return copy(isLoading = true)
    }

    internal fun disableLoading(): CategoryUiState {
        return copy(isLoading = false)
    }

    internal fun toEmptyState(): CategoryUiState {
        return copy(isLoading = false, items = emptyList())
    }

    internal fun toSuccessState(
        games: List<CategoryUiModel>
    ): CategoryUiState {
        return copy(isLoading = false, items = games)
    }

    internal fun hasLoadedNewGames(): Boolean {
        return (!isLoading && items.isNotEmpty())
    }
}

@Immutable
internal data class CategoryUiModel(
    val id: Int,
    val title: String,
    val coverUrl: String?,
)
