package ca.hojat.gamehub.core.domain.games.common.throttling

import ca.hojat.gamehub.core.domain.games.common.throttling.GamesRefreshingThrottler
import ca.hojat.gamehub.core.domain.games.common.throttling.GamesRefreshingThrottlerKeyProvider
import javax.inject.Inject

class GamesRefreshingThrottlerTools @Inject constructor(
    val throttler: GamesRefreshingThrottler,
    val keyProvider: GamesRefreshingThrottlerKeyProvider,
)
