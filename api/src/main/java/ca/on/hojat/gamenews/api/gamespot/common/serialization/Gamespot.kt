package ca.on.hojat.gamenews.api.gamespot.common.serialization

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class Gamespot(val value: String)
