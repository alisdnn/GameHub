package com.paulrybitskyi.gamedge.igdb.api.games.requests

import com.paulrybitskyi.gamedge.igdb.common.ApiRequest

data class GetRecentlyReleasedGamesRequest(
    val minReleaseDateTimestamp: Long,
    val maxReleaseDateTimestamp: Long,
    override val offset: Int,
    override val limit: Int,
) : ApiRequest
