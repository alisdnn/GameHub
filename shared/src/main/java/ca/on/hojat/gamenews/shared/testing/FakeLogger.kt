package ca.on.hojat.gamenews.shared.testing

class FakeLogger {

    private var infoMessage = ""
    var errorMessage = ""

    fun info(message: String) {
        infoMessage = message
    }

    fun error(message: String) {
        errorMessage = message
    }
}
