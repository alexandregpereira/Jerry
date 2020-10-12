package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation

/**
 * Change the visibility to GONE of the view using fade out animation. This method can be
 * reverted in the middle of the animation if the [visibleFadeIn] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.goneFadeOutSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: (() -> Unit)? = null
) {
    hideFadeOutSpring(stiffness, ::gone, onAnimationEnd = onAnimationEnd)
}

/**
 * Change the visibility to INVISIBLE of the view using fade out animation. This method can be
 * reverted in the middle of the animation if the [visibleFadeIn] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.invisibleFadeOutSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: (() -> Unit)? = null
) {
    hideFadeOutSpring(stiffness, ::invisible, onAnimationEnd)
}

/**
 * Change the visibility to VISIBLE of the view using fade in animation. This method can be
 * reverted in the middle of the animation if the [invisibleFadeOut] or [goneFadeOut]
 * method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.visibleFadeInSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
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

    startFadeSpringAnimation(
        targetValue = 1f,
        stiffness = stiffness,
        onAnimationEnd = onAnimationEnd
    )
}

/**
 * Start the fade out animation without changing the visibility status. The changes in the
 * visibility status is delegate to the function [hide].
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.hideFadeOutSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    hide: (() -> Unit),
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isVisible().not() || isFadeOutRunning()) {
        if (isFadeOutRunning().not()) {
            onAnimationEnd?.invoke()
        }
        return
    }
    startFadeOutRunning()

    startFadeSpringAnimation(
        targetValue = 0f,
        stiffness = stiffness,
        onAnimationEnd = {
            hide()
            onAnimationEnd?.invoke()
        }
    )
}

internal fun View.startFadeSpringAnimation(
    targetValue: Float,
    stiffness: Float,
    onAnimationEnd: (() -> Unit)?,
) = startSpringAnimation(
    key = R.string.alpha_spring_key,
    property = DynamicAnimation.ALPHA,
    targetValue = targetValue,
    stiffness = stiffness,
    endListenerPair = R.string.alpha_end_listener_key to {
        clearFadeInFadeOutRunning()
        onAnimationEnd?.invoke()
    }
)
