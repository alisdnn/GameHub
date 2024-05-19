package ca.hojat.gamehub.feature_news.domain.datastores

import ca.hojat.gamehub.core.domain.DomainResult
import ca.hojat.gamehub.core.domain.entities.Pagination
import ca.hojat.gamehub.feature_news.domain.entities.Article

internal interface ArticlesRemoteDataSource {
    suspend fun getArticles(pagination: Pagination): DomainResult<List<Article>>
}
