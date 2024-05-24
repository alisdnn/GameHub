package ca.hojat.gamehub.feature_news.domain.throttling

import javax.inject.Inject

internal class ArticlesRefreshingThrottlerTools @Inject constructor(
    val throttler: ArticlesRefreshingThrottler,
    val keyProvider: ArticlesRefreshingThrottlerKeyProvider,
)
