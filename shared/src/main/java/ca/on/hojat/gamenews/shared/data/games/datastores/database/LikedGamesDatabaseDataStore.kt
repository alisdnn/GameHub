package ca.on.hojat.gamenews.shared.data.games.datastores.database

import ca.on.hojat.gamenews.shared.database.games.entities.DbGame
import ca.on.hojat.gamenews.shared.database.games.tables.LikedGamesTable
import ca.on.hojat.gamenews.shared.domain.common.DispatcherProvider
import ca.on.hojat.gamenews.shared.domain.common.entities.Pagination
import ca.on.hojat.gamenews.shared.domain.games.datastores.LikedGamesLocalDataStore
import ca.on.hojat.gamenews.core.domain.entities.Game
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class LikedGamesDatabaseDataStore @Inject constructor(
    private val likedGamesTable: LikedGamesTable,
    private val likedGameFactory: LikedGameFactory,
    private val dispatcherProvider: DispatcherProvider,
    private val dbGameMapper: DbGameMapper,
) : LikedGamesLocalDataStore {

    override suspend fun likeGame(gameId: Int) {
        likedGamesTable.saveLikedGame(likedGameFactory.createLikedGame(gameId))
    }

    override suspend fun unlikeGame(gameId: Int) {
        likedGamesTable.deleteLikedGame(gameId)
    }

    override suspend fun isGameLiked(gameId: Int): Boolean {
        return likedGamesTable.isGameLiked(gameId)
    }

    override fun observeGameLikeState(gameId: Int): Flow<Boolean> {
        return likedGamesTable.observeGameLikeState(gameId)
    }

    override fun observeLikedGames(pagination: Pagination): Flow<List<Game>> {
        return likedGamesTable.observeLikedGames(
            offset = pagination.offset,
            limit = pagination.limit
        )
            .toDataGamesFlow()
    }

    private fun Flow<List<DbGame>>.toDataGamesFlow(): Flow<List<Game>> {
        return distinctUntilChanged()
            .map(dbGameMapper::mapToDomainGames)
            .flowOn(dispatcherProvider.computation)
    }
}
