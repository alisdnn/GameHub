package ca.on.hojat.gamenews.feature_settings.presentation

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ca.on.hojat.gamenews.shared.ui.CommandsHandler
import ca.on.hojat.gamenews.shared.ui.LocalUrlOpener
import ca.on.hojat.gamenews.shared.ui.NavBarColorHandler
import ca.on.hojat.gamenews.shared.ui.theme.GamedgeTheme
import ca.on.hojat.gamenews.shared.ui.theme.subtitle3
import ca.on.hojat.gamenews.shared.ui.widgets.AnimatedContentContainer
import ca.on.hojat.gamenews.shared.ui.widgets.FiniteUiState
import ca.on.hojat.gamenews.shared.ui.widgets.GameNewsCard
import ca.on.hojat.gamenews.shared.ui.widgets.GamedgeProgressIndicator
import ca.on.hojat.gamenews.shared.ui.widgets.dialogs.GamedgeDialog
import ca.on.hojat.gamenews.shared.ui.widgets.toolbars.Toolbar
import com.paulrybitskyi.commons.ktx.showShortToast
import ca.on.hojat.gamenews.R
import ca.on.hojat.gamenews.feature_settings.domain.entities.Theme

@Composable
fun Settings(modifier: Modifier) {
    Settings(
        viewModel = hiltViewModel(),
        modifier = modifier,
    )
}

@Composable
private fun Settings(
    viewModel: SettingsViewModel,
    modifier: Modifier,
) {
    val urlOpener = LocalUrlOpener.current
    val context = LocalContext.current

    NavBarColorHandler()
    CommandsHandler(viewModel = viewModel) { command ->
        when (command) {
            is SettingsCommand.OpenUrl -> {
                if (!urlOpener.openUrl(command.url, context)) {
                    context.showShortToast(context.getString(R.string.url_opener_not_found))
                }
            }
        }
    }
    Settings(
        uiState = viewModel.uiState.collectAsState().value,
        onSettingClicked = viewModel::onSettingClicked,
        onThemePicked = viewModel::onThemePicked,
        onThemePickerDismissed = viewModel::onThemePickerDismissed,
        modifier = modifier,
    )
}

@Composable
private fun Settings(
    uiState: SettingsUiState,
    onSettingClicked: (SettingsSectionItemUiModel) -> Unit,
    onThemePicked: (Theme) -> Unit,
    onThemePickerDismissed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Toolbar(
                title = stringResource(R.string.settings_toolbar_title),
                contentPadding = WindowInsets.statusBars
                    .only(WindowInsetsSides.Vertical + WindowInsetsSides.Horizontal)
                    .asPaddingValues(),
            )
        },
    ) { paddingValues ->
        AnimatedContentContainer(
            finiteUiState = uiState.finiteUiState,
            modifier = Modifier.padding(paddingValues),
        ) { finiteUiState ->
            when (finiteUiState) {
                FiniteUiState.Loading -> {
                    LoadingState(modifier = Modifier.align(Alignment.Center))
                }
                FiniteUiState.Success -> {
                    SuccessState(
                        sections = uiState.sections,
                        onSettingClicked = onSettingClicked,
                    )
                }
                else -> error("Unsupported finite UI state = $finiteUiState.")
            }
        }
    }

    if (uiState.isThemePickerVisible) {
        ThemePickerDialog(
            uiState = uiState,
            onThemePicked = onThemePicked,
            onPickerDismissed = onThemePickerDismissed,
        )
    }
}

@Composable
private fun LoadingState(modifier: Modifier) {
    GamedgeProgressIndicator(modifier = modifier)
}

@Composable
private fun SuccessState(
    sections: List<SettingsSectionUiModel>,
    onSettingClicked: (SettingsSectionItemUiModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(GamedgeTheme.spaces.spacing_3_5),
    ) {
        items(
            items = sections,
            key = SettingsSectionUiModel::id,
        ) { section ->
            SettingsSection(
                section = section,
                onSettingClicked = onSettingClicked,
            )
        }
    }
}

@Composable
private fun SettingsSection(
    section: SettingsSectionUiModel,
    onSettingClicked: (SettingsSectionItemUiModel) -> Unit,
) {
    GameNewsCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .padding(
                    top = GamedgeTheme.spaces.spacing_4_0,
                    bottom = GamedgeTheme.spaces.spacing_2_0,
                ),
        ) {
            Text(
                text = section.title,
                modifier = Modifier
                    .padding(horizontal = GamedgeTheme.spaces.spacing_4_0)
                    .padding(bottom = GamedgeTheme.spaces.spacing_2_0),
                color = GamedgeTheme.colors.secondary,
                style = GamedgeTheme.typography.subtitle3,
            )

            for (sectionItem in section.items) {
                SettingsSectionItem(
                    sectionItem = sectionItem,
                    contentPadding = PaddingValues(
                        vertical = GamedgeTheme.spaces.spacing_2_0,
                        horizontal = GamedgeTheme.spaces.spacing_4_0,
                    ),
                    onSettingClicked = onSettingClicked,
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionItem(
    sectionItem: SettingsSectionItemUiModel,
    contentPadding: PaddingValues,
    onSettingClicked: (SettingsSectionItemUiModel) -> Unit,
) {
    val clickableModifier = if (sectionItem.isClickable) {
        Modifier.clickable { onSettingClicked(sectionItem) }
    } else {
        Modifier
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(clickableModifier)
            .padding(contentPadding),
    ) {
        Text(
            text = sectionItem.title,
            color = GamedgeTheme.colors.onPrimary,
            style = GamedgeTheme.typography.subtitle3,
        )
        Text(
            text = sectionItem.description,
            modifier = Modifier.padding(top = GamedgeTheme.spaces.spacing_0_5),
            style = GamedgeTheme.typography.body2,
        )
    }
}

@Composable
private fun ThemePickerDialog(
    uiState: SettingsUiState,
    onThemePicked: (Theme) -> Unit,
    onPickerDismissed: () -> Unit,
) {
    GamedgeDialog(onDialogDismissed = onPickerDismissed) {
        Text(
            text = stringResource(R.string.settings_item_theme_title),
            modifier = Modifier
                .padding(horizontal = GamedgeTheme.spaces.spacing_6_0)
                .padding(bottom = GamedgeTheme.spaces.spacing_2_0),
            color = GamedgeTheme.colors.onPrimary,
            style = GamedgeTheme.typography.h5,
        )

        for (theme in Theme.values()) {
            ThemePickerDialogOption(
                isSelected = (theme.name == uiState.selectedThemeName),
                themeTitle = stringResource(theme.uiTextRes),
                onOptionClicked = { onThemePicked(theme) },
            )
        }
    }
}

@Composable
private fun ThemePickerDialogOption(
    isSelected: Boolean,
    themeTitle: String,
    onOptionClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOptionClicked)
            .padding(
                vertical = GamedgeTheme.spaces.spacing_3_0,
                horizontal = GamedgeTheme.spaces.spacing_5_5,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = GamedgeTheme.colors.secondary,
                unselectedColor = GamedgeTheme.colors.onSurface,
            ),
        )

        Text(
            text = themeTitle,
            modifier = Modifier.padding(start = GamedgeTheme.spaces.spacing_4_0),
            style = GamedgeTheme.typography.h6,
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsLoadingStatePreview() {
    GamedgeTheme {
        Settings(
            uiState = SettingsUiState(
                isLoading = false,
                sections = emptyList(),
                selectedThemeName = null,
                isThemePickerVisible = false,
            ),
            onSettingClicked = {},
            onThemePicked = {},
            onThemePickerDismissed = {},
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsSuccessStatePreview() {
    GamedgeTheme {
        Settings(
            uiState = SettingsUiState(
                isLoading = false,
                sections = listOf(
                    SettingsSectionUiModel(
                        id = 1,
                        title = "Section 1",
                        items = listOf(
                            SettingsSectionItemUiModel(
                                id = 1,
                                title = "Title 1",
                                description = "Description 1",
                            ),
                            SettingsSectionItemUiModel(
                                id = 2,
                                title = "Title 2",
                                description = "Description 2",
                            ),
                        )
                    ),
                    SettingsSectionUiModel(
                        id = 2,
                        title = "Section 2",
                        items = listOf(
                            SettingsSectionItemUiModel(
                                id = 3,
                                title = "Title 1",
                                description = "Description 1",
                            ),
                            SettingsSectionItemUiModel(
                                id = 4,
                                title = "Title 2",
                                description = "Description 2",
                            ),
                            SettingsSectionItemUiModel(
                                id = 5,
                                title = "Title 3",
                                description = "Description 3",
                            ),
                        )
                    )
                ),
                selectedThemeName = null,
                isThemePickerVisible = false,
            ),
            onSettingClicked = {},
            onThemePicked = {},
            onThemePickerDismissed = {},
        )
    }
}
