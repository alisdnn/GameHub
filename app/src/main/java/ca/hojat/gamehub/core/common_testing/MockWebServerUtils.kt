package ca.hojat.gamehub.core.common_testing

import okhttp3.mockwebserver.MockWebServer

fun MockWebServer.startSafe() = try {
    start()
} catch (ignore: Throwable) {
    // ignore
}
