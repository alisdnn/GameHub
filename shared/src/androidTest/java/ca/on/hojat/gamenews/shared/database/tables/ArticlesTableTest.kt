package ca.on.hojat.gamenews.shared.database.tables

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import ca.on.hojat.gamenews.shared.database.DB_ARTICLES
import ca.on.hojat.gamenews.shared.database.articles.DatabaseArticle
import ca.on.hojat.gamenews.shared.database.articles.tables.ArticlesTable
import ca.on.hojat.gamenews.shared.database.common.di.DatabaseModule
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
internal class
ArticlesTableTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val executorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var SUT: ArticlesTable

    @Module(includes = [TestDatabaseModule::class])
    @InstallIn(SingletonComponent::class)
    class TestModule

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun saves_and_observes_sorted_articles() {
        runTest {
            SUT.saveArticles(DB_ARTICLES)

            val expectedArticles = DB_ARTICLES.sortedByDescending(DatabaseArticle::publicationDate)

            SUT.observeArticles(offset = 0, limit = DB_ARTICLES.size).test {
                assertThat(awaitItem()).isEqualTo(expectedArticles)
            }
        }
    }
}
