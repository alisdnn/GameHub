package ca.on.hojat.gamenews.shared.domain.games.usecases

import ca.on.hojat.gamenews.shared.domain.common.DispatcherProvider
import ca.on.hojat.gamenews.shared.domain.games.ObservableGamesUseCase
import ca.on.hojat.gamenews.shared.domain.games.common.ObserveGamesUseCaseParams
import ca.on.hojat.gamenews.shared.domain.games.datastores.GamesLocalDataStore
import ca.on.hojat.gamenews.core.domain.entities.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface ObserveMostAnticipatedGamesUseCase : ObservableGamesUseCase

@Singleton
internal class ObserveMostAnticipatedGamesUseCaseImpl @Inject constructor(
    private val gamesLocalDataStore: GamesLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveMostAnticipatedGamesUseCase {

    override fun execute(params: ObserveGamesUseCaseParams): Flow<List<Game>> {
        return gamesLocalDataStore.observeMostAnticipatedGames(params.pagination)
            .flowOn(dispatcherProvider.main)
    }
}
