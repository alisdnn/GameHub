package ca.on.hojat.gamenews.shared.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion

data class Tuple4<T1, T2, T3, T4>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4
)

data class Tuple5<T1, T2, T3, T4, T5>(
    val first: T1,
    val second: T2,
    val third: T3,
    val fourth: T4,
    val fifth: T5
)

fun <T1, T2> combine(
    f1: Flow<T1>,
    f2: Flow<T2>
): Flow<Pair<T1, T2>> {
    return combine(f1, f2) { t1, t2 ->
        Pair(t1, t2)
    }
}

fun <T1, T2, T3> combine(
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>
): Flow<Triple<T1, T2, T3>> {
    return combine(f1, f2, f3) { t1, t2, t3 ->
        Triple(t1, t2, t3)
    }
}

fun <T1, T2, T3, T4> combine(
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>,
    f4: Flow<T4>
): Flow<Tuple4<T1, T2, T3, T4>> {
    return combine(f1, f2, f3, f4) { t1, t2, t3, t4 ->
        Tuple4(t1, t2, t3, t4)
    }
}

fun <T1, T2, T3, T4, T5> combine(
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>,
    f4: Flow<T4>,
    f5: Flow<T5>
): Flow<Tuple5<T1, T2, T3, T4, T5>> {
    return combine(f1, f2, f3, f4, f5) { t1, t2, t3, t4, t5 ->
        Tuple5(t1, t2, t3, t4, t5)
    }
}

fun <T> Flow<T>.onSuccess(action: suspend FlowCollector<T>.() -> Unit): Flow<T> {
    return onCompletion { error ->
        if (error == null) action()
    }
}

fun <T> Flow<T>.onError(action: suspend FlowCollector<T>.(cause: Throwable) -> Unit): Flow<T> =
    catch(action)

fun <T> Flow<T>.onEachError(action: (cause: Throwable) -> Unit): Flow<T> {
    return onError {
        action(it)
        throw it
    }
}
