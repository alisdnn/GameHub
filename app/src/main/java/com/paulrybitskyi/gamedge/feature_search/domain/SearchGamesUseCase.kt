package com.paulrybitskyi.gamedge.feature_search.domain

import ca.on.hojat.gamenews.shared.core.providers.NetworkStateProvider
import ca.on.hojat.gamenews.shared.domain.common.DispatcherProvider
import ca.on.hojat.gamenews.shared.domain.common.DomainResult
import ca.on.hojat.gamenews.shared.domain.common.entities.Pagination
import ca.on.hojat.gamenews.shared.domain.common.extensions.asSuccess
import ca.on.hojat.gamenews.shared.domain.common.extensions.onSuccess
import ca.on.hojat.gamenews.shared.domain.common.usecases.UseCase
import ca.on.hojat.gamenews.shared.domain.games.datastores.GamesDataStores
import ca.on.hojat.gamenews.shared.domain.games.entities.Game
import com.paulrybitskyi.gamedge.feature_search.domain.SearchGamesUseCase.Params
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

internal interface SearchGamesUseCase : UseCase<Params, Flow<DomainResult<List<Game>>>> {

    data class Params(
        val searchQuery: String,
        val pagination: Pagination = Pagination()
    )
}

@Singleton
@BindType
internal class SearchGamesUseCaseImpl @Inject constructor(
    private val gamesDataStores: GamesDataStores,
    private val dispatcherProvider: DispatcherProvider,
    private val networkStateProvider: NetworkStateProvider,
) : SearchGamesUseCase {

    private companion object {
        private const val LOCAL_SEARCH_DELAY_TIMEOUT = 150L
    }

    override suspend fun execute(params: Params): Flow<DomainResult<List<Game>>> {
        return flow { emit(searchGames(params)) }
            .flowOn(dispatcherProvider.main)
    }

    private suspend fun searchGames(params: Params): DomainResult<List<Game>> {
        val searchQuery = params.searchQuery
        val pagination = params.pagination

        if (networkStateProvider.isNetworkAvailable) {
            return gamesDataStores.remote
                .searchGames(searchQuery, pagination)
                .onSuccess(gamesDataStores.local::saveGames)
        }

        return gamesDataStores.local
            .searchGames(searchQuery, pagination)
            .asSuccess()
            // Delaying to give a sense of "loading" since it's really fast without it
            .also { delay(LOCAL_SEARCH_DELAY_TIMEOUT) }
    }
}
