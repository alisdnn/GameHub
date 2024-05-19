package ca.hojat.gamehub.feature_news.domain.throttling

import ca.hojat.gamehub.feature_news.domain.throttling.ArticlesRefreshingThrottler
import ca.hojat.gamehub.feature_news.domain.throttling.ArticlesRefreshingThrottlerKeyProvider
import javax.inject.Inject

internal class ArticlesRefreshingThrottlerTools @Inject constructor(
    val throttler: ArticlesRefreshingThrottler,
    val keyProvider: ArticlesRefreshingThrottlerKeyProvider,
)
