package ca.hojat.gamehub.core.common_testing.domain

import ca.hojat.gamehub.core.domain.common.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * As long as the test rules you create for your coroutines are defined by
 * this class (and this @param dispatcher), they will skip possible delays
 * that you might have in your code.
 */
class MainCoroutineRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    val dispatcherProvider = object : DispatcherProvider {
        override val main = dispatcher
        override val io = dispatcher
        override val computation = dispatcher
    }

    override fun starting(description: Description) {
        super.starting(description)

        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)

        Dispatchers.resetMain()
    }
}
