package ca.on.hojat.gamenews.api.igdb.common

import ca.on.hojat.gamenews.api.BuildConfig
import javax.inject.Inject

internal interface TwitchConstantsProvider {
    val clientId: String
    val clientSecret: String
    val apiBaseUrl: String
}

internal class ProdTwitchConstantsProvider @Inject constructor() : TwitchConstantsProvider {
    override val clientId: String = BuildConfig.TWITCH_APP_CLIENT_ID
    override val clientSecret: String = BuildConfig.TWITCH_APP_CLIENT_SECRET
    override val apiBaseUrl: String = Constants.TWITCH_API_BASE_URL
}
