package com.paulrybitskyi.gamedge.feature.news.domain.usecases

import ca.on.hojat.gamenews.shared.domain.common.DispatcherProvider
import ca.on.hojat.gamenews.shared.domain.common.entities.Pagination
import ca.on.hojat.gamenews.shared.domain.common.usecases.ObservableUseCase
import com.paulrybitskyi.gamedge.feature.news.domain.datastores.ArticlesLocalDataStore
import com.paulrybitskyi.gamedge.feature.news.domain.entities.Article
import com.paulrybitskyi.gamedge.feature.news.domain.usecases.ObserveArticlesUseCase.Params
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

internal interface ObserveArticlesUseCase : ObservableUseCase<Params, List<Article>> {

    data class Params(
        val pagination: Pagination = Pagination()
    )
}

@Singleton
@BindType
internal class ObserveArticlesUseCaseImpl @Inject constructor(
    private val articlesLocalDataStore: ArticlesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveArticlesUseCase {

    override fun execute(params: Params): Flow<List<Article>> {
        return articlesLocalDataStore.observeArticles(params.pagination)
            .flowOn(dispatcherProvider.main)
    }
}
