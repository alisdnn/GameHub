package com.paulrybitskyi.gamedge.feature_settings.presentation

import com.paulrybitskyi.gamedge.R
import com.paulrybitskyi.gamedge.feature_settings.domain.entities.Theme

internal val Theme.uiTextRes: Int
    get() = when (this) {
        Theme.LIGHT -> R.string.settings_item_theme_option_light
        Theme.DARK -> R.string.settings_item_theme_option_dark
        Theme.SYSTEM -> R.string.settings_item_theme_option_system_default
    }
