package ca.on.hojat.gamenews.feature_news.presentation

import app.cash.turbine.test
import ca.on.hojat.gamenews.feature_news.DOMAIN_ARTICLES
import ca.on.hojat.gamenews.shared.testing.FakeErrorMapper
import ca.on.hojat.gamenews.shared.testing.FakeLogger
import ca.on.hojat.gamenews.shared.testing.domain.MainCoroutineRule
import ca.on.hojat.gamenews.shared.ui.base.events.common.GeneralCommand
import ca.on.hojat.gamenews.shared.ui.widgets.FiniteUiState
import com.github.michaelbull.result.Ok
import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.feature_news.domain.DomainArticle
import com.paulrybitskyi.gamedge.feature_news.domain.usecases.ObserveArticlesUseCase
import com.paulrybitskyi.gamedge.feature_news.domain.usecases.RefreshArticlesUseCase
import com.paulrybitskyi.gamedge.feature_news.presentation.GamingNewsCommand
import com.paulrybitskyi.gamedge.feature_news.presentation.GamingNewsViewModel
import com.paulrybitskyi.gamedge.feature_news.presentation.mapping.GamingNewsItemUiModelMapper
import com.paulrybitskyi.gamedge.feature_news.presentation.widgets.GamingNewsItemUiModel
import com.paulrybitskyi.gamedge.feature_news.presentation.widgets.finiteUiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

internal class GamingNewsViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(StandardTestDispatcher())

    private val observeArticlesUseCase = mockk<ObserveArticlesUseCase>(relaxed = true)
    private val refreshArticlesUseCase = mockk<RefreshArticlesUseCase>(relaxed = true)

    private val logger = FakeLogger()
    private val SUT by lazy {
        GamingNewsViewModel(
            observeArticlesUseCase = observeArticlesUseCase,
            refreshArticlesUseCase = refreshArticlesUseCase,
            uiModelMapper = FakeGamingNewsItemUiModelMapper(),
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
            errorMapper = FakeErrorMapper(),
            logger = logger,
        )
    }

    @Test
    fun `Emits correct ui states when loading data`() {
        runTest {
            every { observeArticlesUseCase.execute(any()) } returns flowOf(DOMAIN_ARTICLES)

            SUT.uiState.test {
                val emptyState = awaitItem()
                val loadingState = awaitItem()
                val resultState = awaitItem()

                assertThat(emptyState.finiteUiState).isEqualTo(FiniteUiState.Empty)
                assertThat(loadingState.finiteUiState).isEqualTo(FiniteUiState.Loading)
                assertThat(resultState.finiteUiState).isEqualTo(FiniteUiState.Success)
                assertThat(resultState.news).hasSize(DOMAIN_ARTICLES.size)
            }
        }
    }

    @Test
    fun `Logs error when articles observing use case throws error`() {
        runTest {
            every { observeArticlesUseCase.execute(any()) } returns flow {
                throw IllegalStateException(
                    "error"
                )
            }

            SUT
            advanceUntilIdle()

            assertThat(logger.errorMessage).isNotEmpty()
        }
    }

    @Test
    fun `Dispatches toast showing command when articles observing use case throws error`() {
        runTest {
            every { observeArticlesUseCase.execute(any()) } returns flow {
                throw IllegalStateException(
                    "error"
                )
            }

            SUT.commandFlow.test {
                assertThat(awaitItem()).isInstanceOf(GeneralCommand.ShowLongToast::class.java)
            }
        }
    }

    @Test
    fun `Dispatches url opening command when clicking on news item`() {
        runTest {
            val itemModel = GamingNewsItemUiModel(
                id = 1,
                imageUrl = null,
                title = "",
                lede = "",
                publicationDate = "",
                siteDetailUrl = "site_detail_url",
            )

            SUT.commandFlow.test {
                SUT.onNewsItemClicked(itemModel)

                val command = awaitItem()

                assertThat(command).isInstanceOf(GamingNewsCommand.OpenUrl::class.java)
                assertThat((command as GamingNewsCommand.OpenUrl).url).isEqualTo(itemModel.siteDetailUrl)
            }
        }
    }

    @Test
    fun `Emits correct ui states when refreshing data`() {
        runTest {
            every { refreshArticlesUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_ARTICLES))

            SUT
            advanceUntilIdle()

            SUT.uiState.test {
                SUT.onRefreshRequested()

                assertThat(awaitItem().isRefreshing).isFalse()
                assertThat(awaitItem().isRefreshing).isTrue()
                assertThat(awaitItem().isRefreshing).isFalse()
            }
        }
    }

    private class FakeGamingNewsItemUiModelMapper : GamingNewsItemUiModelMapper {

        override fun mapToUiModel(article: DomainArticle): GamingNewsItemUiModel {
            return GamingNewsItemUiModel(
                id = article.id,
                imageUrl = null,
                title = article.title,
                lede = article.lede,
                publicationDate = "publication_date",
                siteDetailUrl = article.siteDetailUrl,
            )
        }
    }
}
