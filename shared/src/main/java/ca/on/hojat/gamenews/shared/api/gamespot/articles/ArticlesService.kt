package ca.on.hojat.gamenews.shared.api.gamespot.articles

import ca.on.hojat.gamenews.core.data.api.common.ApiResult
import ca.on.hojat.gamenews.core.data.api.gamespot.articles.entities.ApiArticle
import ca.on.hojat.gamenews.core.data.api.gamespot.common.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

internal interface ArticlesService {

    @GET("articles")
    suspend fun getArticles(
        @QueryMap queryParams: Map<String, String>
    ): ApiResult<Response<ApiArticle>>
}
