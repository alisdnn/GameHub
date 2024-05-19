package ca.hojat.gamehub.core.common_testing

import ca.hojat.gamehub.core.mappers.ErrorMapper

class FakeErrorMapper : ErrorMapper {

    override fun mapToMessage(error: Throwable): String {
        return "error"
    }
}
