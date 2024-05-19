package ca.hojat.gamehub.core.common_testing.domain

import ca.hojat.gamehub.core.domain.entities.Pagination
import ca.hojat.gamehub.core.domain.games.common.throttling.GamesRefreshingThrottlerKeyProvider
import ca.hojat.gamehub.core.domain.entities.Company
import ca.hojat.gamehub.core.domain.entities.Game

class FakeGamesRefreshingThrottlerKeyProvider : GamesRefreshingThrottlerKeyProvider {

    override fun providePopularGamesKey(pagination: Pagination): String {
        return "providePopularGamesKey"
    }

    override fun provideRecentlyReleasedGamesKey(pagination: Pagination): String {
        return "provideRecentlyReleasedGamesKey"
    }

    override fun provideComingSoonGamesKey(pagination: Pagination): String {
        return "provideComingSoonGamesKey"
    }

    override fun provideMostAnticipatedGamesKey(pagination: Pagination): String {
        return "provideMostAnticipatedGamesKey"
    }

    override fun provideCompanyDevelopedGamesKey(
        company: Company,
        pagination: Pagination
    ): String {
        return "provideCompanyDevelopedGamesKey"
    }

    override fun provideSimilarGamesKey(game: Game, pagination: Pagination): String {
        return "provideSimilarGamesKey"
    }
}
