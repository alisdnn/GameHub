package ca.on.hojat.gamenews.core.data.api

import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.google.common.truth.Truth.assertThat
import ca.on.hojat.gamenews.core.data.api.common.Error
import ca.on.hojat.gamenews.core.data.api.igdb.auth.AuthService
import ca.on.hojat.gamenews.core.data.api.igdb.auth.entities.ApiOauthCredentials
import ca.on.hojat.gamenews.core.common_testing.startSafe
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class AuthServiceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var mockWebServer: MockWebServer
    @Inject
    lateinit var authService: AuthService

    @Before
    fun setup() {
        hiltRule.inject()
        mockWebServer.startSafe()
    }

    @Test
    fun http_error_is_returned_when_credentials_endpoint_returns_bad_request_response() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setResponseCode(400))

            val error = authService.getOauthCredentials("", "", "").getError()

            assertThat(error is Error.HttpError).isTrue()
        }
    }

    @Test
    fun http_error_with_400_code_is_returned_when_credentials_endpoint_returns_bad_request_response() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setResponseCode(400))

            val error = authService.getOauthCredentials("", "", "").getError()

            assertThat(error is Error.HttpError).isTrue()
            assertThat((error as Error.HttpError).code).isEqualTo(400)
        }
    }

    @Test
    fun http_error_with_proper_error_message_is_returned_when_credentials_endpoint_returns_bad_request_response() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(400)
                    .setBody("{\"message\": \"invalid client secret\"}")
            )

            val error = authService.getOauthCredentials("", "", "").getError()

            assertThat(error is Error.HttpError).isTrue()
            assertThat((error as Error.HttpError).message).isEqualTo("invalid client secret")
        }
    }

    @Test
    fun http_error_with_unknown_error_message_is_returned_when_credentials_endpoint_returns_bad_request_response() {
        runBlocking {
            val errorBody = "{\"error\": \"invalid client secret\"}"

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(400)
                    .setBody(errorBody)
            )

            val error = authService.getOauthCredentials("", "", "").getError()

            assertThat(error is Error.HttpError).isTrue()
            assertThat((error as Error.HttpError).message).isEqualTo("Unknown Error: $errorBody")
        }
    }

    @Test
    fun parsed_credentials_are_returned_when_credentials_endpoint_returns_successful_response() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(
                        """
                        {
                           "access_token": "token",
                           "expires_in": 5643175,
                           "token_type": "bearer"
                        }
                        """.trimIndent()
                    )
            )

            val parsedCredentials = authService.getOauthCredentials("", "", "").get()
            val expectedCredentials = ApiOauthCredentials(
                accessToken = "token",
                tokenType = "bearer",
                tokenTtl = 5643175L
            )

            assertThat(parsedCredentials).isEqualTo(expectedCredentials)
        }
    }

    @Test
    fun unknown_error_is_returned_when_credentials_endpoint_returns_successful_response_with_no_body() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().setResponseCode(200))

            val error = authService.getOauthCredentials("", "", "").getError()

            assertThat(error is Error.UnknownError).isTrue()
        }
    }

    @Test
    fun unknown_error_is_returned_when_credentials_endpoint_returns_successful_response_with_bad_json() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody("{}")
            )

            val error = authService.getOauthCredentials("", "", "").getError()

            assertThat(error is Error.UnknownError).isTrue()
        }
    }

    @Test
    fun network_error_is_returned_when_network_is_disconnected_while_fetching_credentials() {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
            )

            val error = authService.getOauthCredentials("", "", "").getError()

            assertThat(error is Error.NetworkError).isTrue()
        }
    }

    @After
    fun cleanup() {
        mockWebServer.shutdown()
    }
}
