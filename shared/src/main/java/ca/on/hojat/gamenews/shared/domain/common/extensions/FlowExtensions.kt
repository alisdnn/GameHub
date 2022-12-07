package ca.on.hojat.gamenews.shared.domain.common.extensions

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapEither
import com.github.michaelbull.result.mapError
import ca.on.hojat.gamenews.shared.domain.common.DomainException
import com.paulrybitskyi.gamedge.common.domain.common.DomainResult
import ca.on.hojat.gamenews.shared.domain.common.entities.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

fun <T> Flow<DomainResult<T>>.onEachSuccess(action: suspend (T) -> Unit): Flow<DomainResult<T>> {
    return onEach { it.onSuccess(action) }
}

fun <T> Flow<DomainResult<T>>.onEachFailure(action: suspend (Error) -> Unit): Flow<DomainResult<T>> {
    return onEach { it.onFailure(action) }
}

fun <T> Flow<DomainResult<T>>.resultOrError(): Flow<T> {
    return map {
        if (it is Ok) return@map it.value
        if (it is Err) throw DomainException(it.error)

        error("The result is neither Ok nor Err.")
    }
}

fun <S1, E1, S2, E2> Flow<Result<S1, E1>>.mapResult(
    success: (S1) -> S2,
    failure: (E1) -> E2
): Flow<Result<S2, E2>> {
    return map { it.mapEither(success, failure) }
}

fun <S1, S2, E1> Flow<Result<S1, E1>>.mapSuccess(
    success: (S1) -> S2
): Flow<Result<S2, E1>> {
    return map { it.map(success) }
}

fun <S1, E1, E2> Flow<Result<S1, E1>>.mapError(
    failure: (E1) -> E2
): Flow<Result<S1, E2>> {
    return map { it.mapError(failure) }
}
