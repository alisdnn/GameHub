package ca.on.hojat.gamenews.api.gamespot.common

import ca.on.hojat.gamenews.api.BuildConfig
import javax.inject.Inject

internal interface GamespotConstantsProvider {
    val apiKey: String
    val apiBaseUrl: String
}

internal class ProdGamespotConstantsProvider @Inject constructor() : GamespotConstantsProvider {
    override val apiKey: String = BuildConfig.GAMESPOT_API_KEY
    override val apiBaseUrl: String = Constants.GAMESPOT_API_BASE_URL
}
