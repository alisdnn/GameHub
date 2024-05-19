@file:Suppress("MagicNumber")

package ca.hojat.gamehub.core.common_testing

import ca.hojat.gamehub.core.data.api.common.Error as ApiError

val API_ERROR_HTTP = ApiError.HttpError(code = 10, message = "message")
val API_ERROR_NETWORK = ApiError.NetworkError(Exception("message"))
val API_ERROR_UNKNOWN = ApiError.UnknownError(Exception("message"))
