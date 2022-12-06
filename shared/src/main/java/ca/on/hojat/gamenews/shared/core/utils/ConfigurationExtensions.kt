package ca.on.hojat.gamenews.shared.core.utils

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES

val Configuration.isDarkThemeEnabled: Boolean
    get() = ((uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES)
