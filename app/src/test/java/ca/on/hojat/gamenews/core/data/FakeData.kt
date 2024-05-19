package ca.on.hojat.gamenews.core.data

import ca.hojat.gamehub.core.domain.auth.entities.OauthCredentials
import ca.hojat.gamehub.core.domain.entities.Company

internal val DOMAIN_OAUTH_CREDENTIALS = OauthCredentials(
    accessToken = "access_token",
    tokenType = "token_type",
    tokenTtl = 5000L,
)

internal val DOMAIN_COMPANY = Company(
    id = 1,
    name = "name",
    websiteUrl = "website_url",
    logo = null,
    developedGames = listOf(1, 2, 3),
)
