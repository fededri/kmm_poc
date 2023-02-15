package com.fededri.kmmpoc
import android.content.res.Resources
import android.util.DisplayMetrics

actual class OrientationProvider {
    actual fun supportsMasterDetail(): Boolean {
        val displayMetrics = Resources.getSystem().displayMetrics
        val widthDpis = convertPixelsToDp(displayMetrics.widthPixels, displayMetrics)
        val heightDpis = convertPixelsToDp(displayMetrics.heightPixels, displayMetrics)

        return displayMetrics.densityDpi >= DisplayMetrics.DENSITY_MEDIUM &&
                widthDpis >= 768
    }

    private fun convertPixelsToDp(px: Int, displayMetrics: DisplayMetrics): Float {
        return px / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}