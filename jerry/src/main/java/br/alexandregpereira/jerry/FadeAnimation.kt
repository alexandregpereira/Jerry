package br.alexandregpereira.jerry

import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Interpolator

/**
 * Change the visibility to GONE of the view using fade out animation. This method can be
 * reverted in the middle of the animation if the [visibleFadeIn] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.goneFadeOut(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) {
    hideFadeOut(duration, ::gone, onAnimationEnd = onAnimationEnd)
}

/**
 * Change the visibility to INVISIBLE of the view using fade out animation. This method can be
 * reverted in the middle of the animation if the [visibleFadeIn] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.invisibleFadeOut(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) {
    hideFadeOut(duration, ::invisible, onAnimationEnd)
}

/**
 * Change the visibility to VISIBLE of the view using fade in animation. This method can be
 * reverted in the middle of the animation if the [invisibleFadeOut] or [goneFadeOut]
 * method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.visibleFadeIn(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isFadeInRunning() || (alpha == 1f && isVisible() && isFadeOutRunning().not())) {
        if (isFadeInRunning().not()) {
            onAnimationEnd?.invoke()
        }
        return
    }
    startFadeInRunning()
    if (alpha == 1f) alpha = 0f
    visible()
    fadeInAnimation(duration = duration).withEndAction {
        clearFadeInFadeOutRunning()
        onAnimationEnd?.invoke()
    }
}

/**
 * Start the fade out animation without changing the visibility status. The changes in the
 * visibility status is delegate to the function [hide].
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [goneFadeOut]
 * @see [invisibleFadeOut]
 */
fun View.hideFadeOut(
    duration: Long = ANIMATION_SHORT_TIME,
    hide: (() -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isVisible().not() || isFadeOutRunning()) {
        if (isFadeOutRunning().not()) {
            onAnimationEnd?.invoke()
        }
        return
    }
    startFadeOutRunning()
    fadeOutAnimation(duration).withEndAction {
        hide?.invoke()
        clearFadeInFadeOutRunning()
        onAnimationEnd?.invoke()
    }
}

fun View.fadeOutAnimation(
    duration: Long = ANIMATION_SHORT_TIME,
    interpolator: Interpolator? = null
): ViewPropertyAnimator {
    return animateFadeVisibility(0f, duration, interpolator)
}

fun View.fadeInAnimation(
    duration: Long = ANIMATION_SHORT_TIME,
    interpolator: Interpolator? = null
): ViewPropertyAnimator {
    return animateFadeVisibility(1f, duration, interpolator)
}

fun View.animateFadeVisibility(
    targetAlpha: Float,
    duration: Long = ANIMATION_SHORT_TIME,
    interpolator: Interpolator? = null
): ViewPropertyAnimator {
    return animate()
        .alpha(targetAlpha)
        .setDuration(duration)
        .apply { interpolator?.let { setInterpolator(it) } }
}
