package ca.on.hojat.gamenews.shared.domain.games.common.throttling

import ca.on.hojat.gamenews.shared.domain.common.entities.Pagination
import ca.on.hojat.gamenews.core.domain.entities.Company
import ca.on.hojat.gamenews.core.domain.entities.Game
import javax.inject.Inject
import javax.inject.Singleton

interface GamesRefreshingThrottlerKeyProvider {
    fun providePopularGamesKey(pagination: Pagination): String
    fun provideRecentlyReleasedGamesKey(pagination: Pagination): String
    fun provideComingSoonGamesKey(pagination: Pagination): String
    fun provideMostAnticipatedGamesKey(pagination: Pagination): String
    fun provideCompanyDevelopedGamesKey(company: Company, pagination: Pagination): String
    fun provideSimilarGamesKey(game: Game, pagination: Pagination): String
}

@Singleton
internal class GamesRefreshingThrottlerKeyProviderImpl @Inject constructor() :
    GamesRefreshingThrottlerKeyProvider {

    override fun providePopularGamesKey(pagination: Pagination): String {
        return "popular_games | offset: ${pagination.offset} | limit: ${pagination.limit}"
    }

    override fun provideRecentlyReleasedGamesKey(pagination: Pagination): String {
        return "recently_released_games | offset: ${pagination.offset} | limit: ${pagination.limit}"
    }

    override fun provideComingSoonGamesKey(pagination: Pagination): String {
        return "coming_soon_games | offset: ${pagination.offset} | limit: ${pagination.limit}"
    }

    override fun provideMostAnticipatedGamesKey(pagination: Pagination): String {
        return "most_anticipated_games | offset: ${pagination.offset} | limit: ${pagination.limit}"
    }

    override fun provideCompanyDevelopedGamesKey(company: Company, pagination: Pagination): String {
        return """
            company_developed_games |
            company_id: ${company.id} |
            developed_games_ids: ${company.developedGames.sorted()} |
            offset: ${pagination.offset} |
            limit: ${pagination.limit}
        """.trimIndent()
    }

    override fun provideSimilarGamesKey(game: Game, pagination: Pagination): String {
        return """
            similar_games |
            game_id: ${game.id} |
            similar_games_ids: ${game.similarGames.sorted()} |
            offset: ${pagination.offset} |
            limit: ${pagination.limit}
        """.trimIndent()
    }
}
