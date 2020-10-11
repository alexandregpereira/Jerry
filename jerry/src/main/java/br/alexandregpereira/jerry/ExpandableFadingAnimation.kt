package br.alexandregpereira.jerry

import android.view.View

/**
 * Uses the [expandHeight] and [visibleFadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseHeightFading] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.expandHeightFading(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = expandFading(duration, isHeight = true, onAnimationEnd)

/**
 * Uses the [expandWidth] and [visibleFadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseWidthFading] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.expandWidthFading(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = expandFading(duration, isHeight = false, onAnimationEnd)

/**
 * Uses the [hideFadeOut] and [collapseHeight] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandHeightFading] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.collapseHeightFading(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = collapseFading(duration, isHeight = true, onAnimationEnd)

/**
 * Uses the [hideFadeOut] and [collapseWidth] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandWidthFading] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.collapseWidthFading(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = collapseFading(duration, isHeight = false, onAnimationEnd)

private fun View.collapseFading(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isExpandingRunning()) {
        collapse(duration = duration, isHeight = isHeight, onAnimationEnd = onAnimationEnd)
        return
    }

    hideFadeOut(duration = duration / 2) {
        collapse(duration = duration / 2, isHeight = isHeight, onAnimationEnd = onAnimationEnd)
    }
}

private fun View.expandFading(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (alpha == 1f && (isVisible() && isCollapsingRunning().not())) {
        return
    }
    if (alpha == 1f) alpha = 0f

    if (alpha > 0f && alpha < 1f) {
        visibleFadeIn(duration = duration, onAnimationEnd = onAnimationEnd)
        return
    }

    expand(duration = duration / 2, isHeight = isHeight) {
        visibleFadeIn(duration = duration / 2, onAnimationEnd = onAnimationEnd)
    }
}