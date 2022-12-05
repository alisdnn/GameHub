package com.paulrybitskyi.gamedge.igdb.api.common.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
internal annotation class Endpoint(val type: Type) {

    enum class Type {
        AUTH,
        GAMES
    }
}
