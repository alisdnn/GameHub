package ca.on.hojat.gamenews.shared.domain.games.entities

enum class AgeRatingCategory(val title: String) {
    UNKNOWN(title = ""),
    ESRB(title = "ESRB"),
    PEGI(title = "PEGI"),
}
