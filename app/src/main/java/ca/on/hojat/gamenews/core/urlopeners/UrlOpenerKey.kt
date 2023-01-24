package ca.on.hojat.gamenews.core.urlopeners

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
annotation class UrlOpenerKey(val type: Type) {

    /**
     * All the different kinds of url openers we have in our app.
     */
    enum class Type {
        NATIVE_APP,
        CUSTOM_TAB,
        BROWSER
    }
}
