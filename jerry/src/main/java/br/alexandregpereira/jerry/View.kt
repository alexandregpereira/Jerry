package br.alexandregpereira.jerry

import android.content.res.Resources
import android.util.TypedValue
import android.view.View

fun View.gone(): View {
    visibility = View.GONE
    return this
}

fun View.invisible(): View {
    visibility = View.INVISIBLE
    return this
}

fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}

fun View.visibleOrGone(visible: Boolean): View {
    if (visible) visible() else gone()
    return this
}

fun visible(vararg views: View) {
    views.forEach { it.visibility = View.VISIBLE }
}

fun gone(vararg views: View) {
    views.forEach { it.visibility = View.GONE }
}

fun invisible(vararg views: View) {
    views.forEach { it.visibility = View.INVISIBLE }
}

fun View.visibleOrInvisible(visible: Boolean): View {
    if (visible) visible() else invisible()
    return this
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun Int.dpToPx(resources: Resources): Int {
    return this.toFloat().dpToPx(resources).toInt()
}

fun Float.dpToPx(resources: Resources): Float {
    return unitToPx(TypedValue.COMPLEX_UNIT_DIP, resources)
}

fun Int.spToPx(resources: Resources): Int {
    return this.toFloat().spToPx(resources).toInt()
}

fun Float.spToPx(resources: Resources): Float {
    return unitToPx(TypedValue.COMPLEX_UNIT_SP, resources)
}

private fun Float.unitToPx(complexUnit: Int, resources: Resources): Float {
    return TypedValue.applyDimension(
        complexUnit,
        this,
        resources.displayMetrics
    )
}
