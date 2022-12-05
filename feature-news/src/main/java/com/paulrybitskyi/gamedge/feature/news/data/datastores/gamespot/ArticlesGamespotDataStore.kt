package com.paulrybitskyi.gamedge.feature.news.data.datastores.gamespot

import com.github.michaelbull.result.mapEither
import com.paulrybitskyi.gamedge.igdb.common.ApiResult
import com.paulrybitskyi.gamedge.common.data.common.ApiErrorMapper
import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import com.paulrybitskyi.gamedge.common.domain.common.DomainResult
import com.paulrybitskyi.gamedge.common.domain.common.entities.Pagination
import com.paulrybitskyi.gamedge.feature.news.domain.datastores.ArticlesRemoteDataStore
import com.paulrybitskyi.gamedge.feature.news.domain.entities.Article
import com.paulrybitskyi.gamedge.igdb.gamespot.articles.ArticlesEndpoint
import com.paulrybitskyi.gamedge.igdb.gamespot.articles.entities.ApiArticle
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class ArticlesGamespotDataStore @Inject constructor(
    private val articlesEndpoint: ArticlesEndpoint,
    private val dispatcherProvider: DispatcherProvider,
    private val apiArticleMapper: GamespotArticleMapper,
    private val apiErrorMapper: ApiErrorMapper,
) : ArticlesRemoteDataStore {

    override suspend fun getArticles(pagination: Pagination): DomainResult<List<Article>> {
        return articlesEndpoint
            .getArticles(pagination.offset, pagination.limit)
            .toDataStoreResult()
    }

    private suspend fun ApiResult<List<ApiArticle>>.toDataStoreResult(): DomainResult<List<Article>> {
        return withContext(dispatcherProvider.computation) {
            mapEither(apiArticleMapper::mapToDomainArticles, apiErrorMapper::mapToDomainError)
        }
    }
}
