package ca.hojat.gamehub.core.common_testing.domain

import ca.hojat.gamehub.core.domain.entities.Error
import ca.hojat.gamehub.core.domain.entities.Pagination
import ca.hojat.gamehub.core.domain.entities.Category
import ca.hojat.gamehub.core.domain.entities.Game

val DOMAIN_GAME = Game(
    id = 1,
    followerCount = null,
    hypeCount = null,
    releaseDate = null,
    criticsRating = null,
    usersRating = null,
    totalRating = null,
    name = "name",
    summary = null,
    storyline = null,
    category = Category.UNKNOWN,
    cover = null,
    releaseDates = listOf(),
    ageRatings = listOf(),
    videos = listOf(),
    artworks = listOf(),
    screenshots = listOf(),
    genres = listOf(),
    platforms = listOf(),
    playerPerspectives = listOf(),
    themes = listOf(),
    modes = listOf(),
    keywords = listOf(),
    involvedCompanies = listOf(),
    websites = listOf(),
    similarGames = listOf(),
)
val DOMAIN_GAMES = listOf(
    DOMAIN_GAME.copy(id = 1),
    DOMAIN_GAME.copy(id = 2),
    DOMAIN_GAME.copy(id = 3),
)

val DOMAIN_ERROR_UNKNOWN = Error.Unknown("message")

val PAGINATION = Pagination(offset = 0, limit = 20)
