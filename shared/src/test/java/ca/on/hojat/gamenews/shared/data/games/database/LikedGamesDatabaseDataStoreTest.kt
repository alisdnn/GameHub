package ca.on.hojat.gamenews.shared.data.games.database

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import ca.on.hojat.gamenews.shared.data.games.datastores.database.DbGameMapper
import ca.on.hojat.gamenews.shared.data.games.datastores.database.LikedGameFactory
import ca.on.hojat.gamenews.shared.data.games.datastores.database.LikedGamesDatabaseDataStore
import ca.on.hojat.gamenews.shared.data.games.datastores.database.mapToDatabaseGames
import com.paulrybitskyi.gamedge.common.testing.domain.DOMAIN_GAMES
import com.paulrybitskyi.gamedge.common.testing.domain.MainCoroutineRule
import com.paulrybitskyi.gamedge.common.testing.domain.PAGINATION
import com.paulrybitskyi.gamedge.database.games.entities.DbGame
import com.paulrybitskyi.gamedge.database.games.entities.DbLikedGame
import com.paulrybitskyi.gamedge.database.games.tables.LikedGamesTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val GAME_ID = 100
private const val ANOTHER_GAME_ID = 110

internal class LikedGamesDatabaseDataStoreTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var likedGamesTable: FakeLikedGamesTable
    private lateinit var dbGameMapper: DbGameMapper
    private lateinit var SUT: LikedGamesDatabaseDataStore

    @Before
    fun setup() {
        likedGamesTable = FakeLikedGamesTable()
        dbGameMapper = DbGameMapper()
        SUT = LikedGamesDatabaseDataStore(
            likedGamesTable = likedGamesTable,
            likedGameFactory = FakeLikedGameFactory(),
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
            dbGameMapper = dbGameMapper
        )
    }

    @Test
    fun `Likes game successfully`() {
        runTest {
            SUT.likeGame(GAME_ID)

            assertThat(SUT.isGameLiked(GAME_ID)).isTrue()
        }
    }

    @Test
    fun `Unlikes game successfully`() {
        runTest {
            SUT.likeGame(GAME_ID)
            SUT.unlikeGame(GAME_ID)

            assertThat(SUT.isGameLiked(GAME_ID)).isFalse()
        }
    }

    @Test
    fun `Validates that unliked game is unliked`() {
        runTest {
            assertThat(SUT.isGameLiked(gameId = ANOTHER_GAME_ID)).isFalse()
        }
    }

    @Test
    fun `Observes game like state successfully`() {
        runTest {
            SUT.likeGame(GAME_ID)

            SUT.observeGameLikeState(GAME_ID).test {
                assertThat(awaitItem()).isTrue()
                awaitComplete()
            }

            SUT.observeGameLikeState(ANOTHER_GAME_ID).test {
                assertThat(awaitItem()).isFalse()
                awaitComplete()
            }
        }
    }

    @Test
    fun `Observes liked games successfully`() {
        runTest {
            val dbGames = dbGameMapper.mapToDatabaseGames(DOMAIN_GAMES)

            likedGamesTable.dbGamesToObserve = dbGames

            SUT.observeLikedGames(PAGINATION).test {
                assertThat(awaitItem()).isEqualTo(DOMAIN_GAMES)
                awaitComplete()
            }
        }
    }

    private class FakeLikedGamesTable : LikedGamesTable {

        var likedGamesMap = mutableMapOf<Int, DbLikedGame>()
        var dbGamesToObserve = listOf<DbGame>()

        override suspend fun saveLikedGame(likedGame: DbLikedGame) {
            likedGamesMap[likedGame.gameId] = likedGame
        }

        override suspend fun deleteLikedGame(gameId: Int) {
            likedGamesMap.remove(gameId)
        }

        override suspend fun isGameLiked(gameId: Int): Boolean {
            return likedGamesMap.containsKey(gameId)
        }

        override fun observeGameLikeState(gameId: Int): Flow<Boolean> {
            return flowOf(likedGamesMap.contains(gameId))
        }

        override fun observeLikedGames(offset: Int, limit: Int): Flow<List<DbGame>> {
            return flowOf(dbGamesToObserve)
        }
    }

    private class FakeLikedGameFactory : LikedGameFactory {

        override fun createLikedGame(gameId: Int): DbLikedGame {
            return DbLikedGame(
                id = 1,
                gameId = gameId,
                likeTimestamp = 500L
            )
        }
    }
}
