package ca.on.hojat.gamenews.feature_likes.domain

import app.cash.turbine.test
import ca.on.hojat.gamenews.shared.domain.games.common.ObserveGamesUseCaseParams
import ca.on.hojat.gamenews.shared.domain.games.datastores.LikedGamesLocalDataStore
import ca.on.hojat.gamenews.shared.testing.domain.DOMAIN_GAMES
import ca.on.hojat.gamenews.shared.testing.domain.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.feature_likes.domain.ObserveLikedGamesUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val OBSERVE_GAMES_USE_CASE_PARAMS = ObserveGamesUseCaseParams()

internal class ObserveLikedGamesUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var likedGamesLocalDataStore: LikedGamesLocalDataStore

    private lateinit var SUT: ObserveLikedGamesUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        SUT = ObserveLikedGamesUseCaseImpl(
            likedGamesLocalDataStore = likedGamesLocalDataStore,
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
        )
    }

    @Test
    fun `Emits liked games successfully`() {
        runTest {
            every { likedGamesLocalDataStore.observeLikedGames(any()) } returns flowOf(DOMAIN_GAMES)

            SUT.execute(OBSERVE_GAMES_USE_CASE_PARAMS).test {
                assertThat(awaitItem()).isEqualTo(DOMAIN_GAMES)
                awaitComplete()
            }
        }
    }
}
