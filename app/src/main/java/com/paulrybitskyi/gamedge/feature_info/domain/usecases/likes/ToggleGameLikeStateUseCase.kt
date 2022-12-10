package com.paulrybitskyi.gamedge.feature_info.domain.usecases.likes

import ca.on.hojat.gamenews.shared.domain.common.usecases.UseCase
import ca.on.hojat.gamenews.shared.domain.games.datastores.LikedGamesLocalDataStore
import com.paulrybitskyi.gamedge.feature_info.domain.usecases.likes.ToggleGameLikeStateUseCase.Params
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject
import javax.inject.Singleton

internal interface ToggleGameLikeStateUseCase : UseCase<Params, Unit> {

    data class Params(val gameId: Int)
}

@Singleton
@BindType
internal class ToggleGameLikeStateUseCaseImpl @Inject constructor(
    private val likedGamesLocalDataStore: LikedGamesLocalDataStore,
) : ToggleGameLikeStateUseCase {

    override suspend fun execute(params: Params) {
        if (likedGamesLocalDataStore.isGameLiked(params.gameId)) {
            likedGamesLocalDataStore.unlikeGame(params.gameId)
        } else {
            likedGamesLocalDataStore.likeGame(params.gameId)
        }
    }
}
