package ca.hojat.gamehub.core.urlopeners

import android.content.Context
import android.content.Intent
import android.net.Uri
import ca.hojat.gamehub.core.extensions.attachNewTaskFlagIfNeeded
import com.paulrybitskyi.hiltbinder.BindType
import timber.log.Timber
import javax.inject.Inject

@BindType(withQualifier = true)
@UrlOpenerKey(UrlOpenerKey.Type.BROWSER)
internal class BrowserUrlOpener @Inject constructor() : UrlOpener {

    override fun openUrl(url: String, context: Context): Boolean {
        val intent = createIntent(url, context)

        return try {
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            Timber.e(e)
            false
        }

    }

    private fun createIntent(url: String, context: Context): Intent {
        return Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            attachNewTaskFlagIfNeeded(context)
        }
    }
}
