@file:JvmName("ContextUtils")
@file:Suppress("TooManyFunctions")

package ca.on.hojat.gamenews.shared.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.use
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import ca.on.hojat.gamenews.shared.commons.core.SdkInfo
import ca.on.hojat.gamenews.shared.commons.device_info.model.DeviceInfo
import ca.on.hojat.gamenews.shared.commons.device_info.model.ProductInfo
import ca.on.hojat.gamenews.shared.commons.device_info.model.screen.ScreenDensity.Companion.asScreenDensity
import ca.on.hojat.gamenews.shared.commons.device_info.model.screen.ScreenDimension
import ca.on.hojat.gamenews.shared.commons.device_info.model.screen.ScreenMetrics
import ca.on.hojat.gamenews.shared.commons.device_info.model.screen.ScreenScalingFactors
import ca.on.hojat.gamenews.shared.commons.device_info.model.screen.ScreenSizeCategory
import ca.on.hojat.gamenews.shared.commons.device_info.model.screen.ScreenSizeCategory.Companion.asScreenSizeCategory
import java.util.Locale

val Context.actionBarSize: Int
    get() = getDimensionPixelSize(
        getResourceIdFromAttributes(
            attributes = intArrayOf(android.R.attr.actionBarSize),
            index = 0
        )
    )

val Context.statusBarHeight: Int
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    get() = resources.run {
        getDimensionPixelSize(
            getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
            )
        )
    }

val Context.navigationBarHeight: Int
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    get() = resources.run {
        getDimensionPixelSize(
            getIdentifier(
                "navigation_bar_height",
                "dimen",
                "android"
            )
        )
    }

val Context.selectableItemBackground: Drawable?
    get() = getAttributeDrawable(android.R.attr.selectableItemBackground)

val Context.selectableItemBackgroundBorderless: Drawable?
    get() = getAttributeDrawable(android.R.attr.selectableItemBackgroundBorderless)

val Context.displayMetrics: DisplayMetrics
    get() = resources.displayMetrics

@get:Suppress("DEPRECATION")
@get:SuppressLint("NewApi")
val Context.locale: Locale
    get() = with(resources.configuration) {
        if (SdkInfo.IS_AT_LEAST_NOUGAT) {
            locales[0]
        } else {
            locale
        }
    }

val Context.configuration: Configuration
    get() = resources.configuration

val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

val Context.notificationManager: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

@Suppress("DEPRECATION")
val Context.fingerprintManager: FingerprintManagerCompat
    get() = FingerprintManagerCompat.from(this)

fun Context.getCompatColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.getDimensionPixelSize(@DimenRes dimenId: Int): Int {
    return resources.getDimensionPixelSize(dimenId)
}

fun Context.getInteger(@IntegerRes intId: Int): Int {
    return resources.getInteger(intId)
}

fun Context.getDimension(@DimenRes dimenId: Int): Float {
    return resources.getDimension(dimenId)
}

fun Context.getFloat(@IntegerRes floatId: Int): Float {
    return TypedValue()
        .also { resources.getValue(floatId, it, true) }
        .let(TypedValue::getFloat)
}

fun Context.getFont(@FontRes fontId: Int): Typeface? {
    return ResourcesCompat.getFont(this, fontId)
}

fun Context.getCompatDrawable(@DrawableRes drawableId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableId)
}

fun Context.getColoredDrawable(@DrawableRes drawableId: Int, @ColorInt color: Int): Drawable? {
    return getCompatDrawable(drawableId)?.setColor(color)
}

fun Context.getColoredStrokeDrawable(
    @DrawableRes drawableId: Int,
    @ColorInt strokeColor: Int,
    strokeWidth: Int
): Drawable? {
    return getCompatDrawable(drawableId)
        ?.run { mutate() as? GradientDrawable }
        ?.apply { setStroke(strokeWidth, strokeColor) }
}

fun Context.getResourceIdFromAttributes(attributes: IntArray, index: Int): Int {
    return obtainStyledAttributes(attributes).use {
        it.getResourceId(index, 0)
    }
}

fun Context.getAttributeDrawable(resId: Int): Drawable? {
    return TypedValue()
        .also { theme.resolveAttribute(resId, it, true) }
        .let { getCompatDrawable(it.resourceId) }
}

fun Context.inflateView(
    @LayoutRes layoutResourceId: Int,
    root: ViewGroup?,
    attachToRoot: Boolean = true
): View {
    return layoutInflater.inflate(
        layoutResourceId,
        root,
        attachToRoot
    )
}

fun Context.showShortToast(message: CharSequence): Toast {
    return showToast(message, duration = Toast.LENGTH_SHORT)
}

fun Context.showLongToast(message: CharSequence): Toast {
    return showToast(message, duration = Toast.LENGTH_LONG)
}

fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, message, duration)
        .apply { show() }
}

fun Context.isPermissionGranted(permission: String): Boolean {
    return (ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED)
}

fun Context.isPermissionDenied(permission: String): Boolean {
    return (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED)
}

fun Context.arePermissionsGranted(permissions: Set<String>): Boolean {
    return permissions.all { isPermissionGranted(it) }
}

fun Context.arePermissionsDenied(permissions: Set<String>): Boolean {
    return permissions.all { isPermissionDenied(it) }
}

/**
 * Checks whether the intent can be handled by some activity
 * on the device or not.
 *
 * Note: Due to [Android 11 package visibility changes](https://g.co/dev/packagevisibility), this
 * method does not work on Android 11 and above.
 *
 * @param intent The intent to check
 *
 * @return true if can be handled; false otherwise
 */
@SuppressLint("QueryPermissionsNeeded")
fun Context.canIntentBeHandled(intent: Intent): Boolean {
    return packageManager.queryIntentActivities(intent, 0).isNotEmpty()
}

/**
 * Creates an intent for a type the class of which is passed
 * as a reified parameter (e.g. Activity, Service, etc.).
 *
 * @return The intent for the particular class
 */
inline fun <reified T> Context.intentFor(): Intent {
    return Intent(this, T::class.java)
}

inline fun <reified T : Any> Context.getSystemService(): T {
    return checkNotNull(ContextCompat.getSystemService(this, T::class.java)) {
        "The service ${T::class.java.simpleName} could not be retrieved."
    }
}


val Context.deviceInfo: DeviceInfo
    get() = DeviceInfo(
        productInfo = productInfo,
        screenMetrics = screenMetrics
    )

val Context.productInfo: ProductInfo
    get() = ProductInfo(
        modelName = Build.MODEL,
        productName = Build.PRODUCT,
        manufacturerName = Build.MANUFACTURER
    )

val Context.screenMetrics: ScreenMetrics
    get() = ScreenMetrics(
        width = displayMetrics.getScreenWidth(this),
        height = displayMetrics.getScreenHeight(this),
        sizeCategory = configuration.getScreenSizeCategory(),
        density = displayMetrics.densityDpi.asScreenDensity(),
        scalingFactors = displayMetrics.getScreenScalingFactors(),
        smallestWidthInDp = configuration.smallestScreenWidthDp
    )

private fun DisplayMetrics.getScreenWidth(context: Context): ScreenDimension {
    return ScreenDimension(
        sizeInPixels = widthPixels,
        sizeInDp = widthPixels.toFloat().pxToDp(context)
    )
}

private fun DisplayMetrics.getScreenHeight(context: Context): ScreenDimension {
    return ScreenDimension(
        sizeInPixels = heightPixels,
        sizeInDp = heightPixels.toFloat().pxToDp(context)
    )
}

private fun DisplayMetrics.getScreenScalingFactors(): ScreenScalingFactors {
    return ScreenScalingFactors(
        pixelScalingFactor = density,
        textPixelScalingFactor = scaledDensity
    )
}

private fun Configuration.getScreenSizeCategory(): ScreenSizeCategory {
    return (screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK).asScreenSizeCategory()
}
