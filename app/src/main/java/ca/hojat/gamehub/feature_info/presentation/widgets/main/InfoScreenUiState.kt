package ca.hojat.gamehub.feature_info.presentation.widgets.main

import androidx.compose.runtime.Immutable
import ca.hojat.gamehub.common_ui.widgets.FiniteUiState

@Immutable
internal data class InfoScreenUiState(
    val isLoading: Boolean,
    val game: InfoScreenUiModel?,
) {

    internal val finiteUiState: FiniteUiState
        get() = when {
            isInEmptyState -> FiniteUiState.EMPTY
            isInLoadingState -> FiniteUiState.LOADING
            isInSuccessState -> FiniteUiState.SUCCESS
            else -> error("Unknown game info UI state.")
        }

    private val isInEmptyState: Boolean
        get() = (!isLoading && (game == null))

    private val isInLoadingState: Boolean
        get() = (isLoading && (game == null))

    private val isInSuccessState: Boolean
        get() = (game != null)

    internal fun toEmptyState(): InfoScreenUiState {
        return copy(isLoading = false, game = null)
    }

    internal fun toLoadingState(): InfoScreenUiState {
        return copy(isLoading = true)
    }

    internal fun toSuccessState(game: InfoScreenUiModel): InfoScreenUiState {
        return copy(isLoading = false, game = game)
    }

}
