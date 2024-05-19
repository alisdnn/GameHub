package ca.hojat.gamehub.core.domain.games.repository

import ca.hojat.gamehub.core.domain.games.repository.GamesLocalDataSource
import ca.hojat.gamehub.core.domain.games.repository.GamesRemoteDataSource
import javax.inject.Inject

class GamesRepository @Inject constructor(
    val local: GamesLocalDataSource,
    val remote: GamesRemoteDataSource
)
