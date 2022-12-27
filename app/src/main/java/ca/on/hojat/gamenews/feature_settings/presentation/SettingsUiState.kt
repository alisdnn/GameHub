package ca.on.hojat.gamenews.feature_settings.presentation

import androidx.compose.runtime.Immutable
import ca.on.hojat.gamenews.core.common_ui.widgets.FiniteUiState

@Immutable
internal data class SettingsUiState(
    val isLoading: Boolean,
    val sections: List<SettingsSectionUiModel>,
    val selectedThemeName: String?,
    val isThemePickerVisible: Boolean,
)

internal val SettingsUiState.finiteUiState: FiniteUiState
    get() = when {
        isInLoadingState -> FiniteUiState.Loading
        isInSuccessState -> FiniteUiState.Success
        else -> error("Unknown settings UI state.")
    }

private val SettingsUiState.isInLoadingState: Boolean
    get() = (isLoading && sections.isEmpty())

private val SettingsUiState.isInSuccessState: Boolean
    get() = sections.isNotEmpty()

internal fun SettingsUiState.toLoadingState(): SettingsUiState {
    return copy(isLoading = true)
}

internal fun SettingsUiState.toSuccessState(
    sections: List<SettingsSectionUiModel>,
    selectedThemeName: String,
): SettingsUiState {
    return copy(
        isLoading = false,
        sections = sections,
        selectedThemeName = selectedThemeName,
    )
}

@Immutable
internal data class SettingsSectionUiModel(
    val id: Int,
    val title: String,
    val items: List<SettingsSectionItemUiModel>,
)

@Immutable
internal data class SettingsSectionItemUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val isClickable: Boolean = true,
)

internal enum class SettingSection(val id: Int) {
    APPEARANCE(id = 1),
    ABOUT(id = 2),
}

internal enum class SettingItem(val id: Int) {
    THEME(id = 1),
    SOURCE_CODE(id = 2),
    VERSION(id = 3),
}
