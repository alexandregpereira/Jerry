package br.alexandregpereira.jerry

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
