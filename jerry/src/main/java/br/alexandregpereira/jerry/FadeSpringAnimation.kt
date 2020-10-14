package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation

fun View.animateAlphaVisibility(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (visible) {
        fadeInSpring(stiffness, onAnimationEnd = onAnimationEnd)
    } else {
        fadeOutSpring(stiffness, onAnimationEnd = onAnimationEnd)
    }
}

/**
 * Change the visibility to GONE of the view using fade out animation. This method can be
 * reverted in the middle of the animation if the [fadeIn] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.fadeOutSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    hideFadeOutSpring(stiffness, hide = ::gone, onAnimationEnd = onAnimationEnd)
}

/**
 * Change the visibility to VISIBLE of the view using fade in animation. This method can be
 * reverted in the middle of the animation if the [fadeOut]
 * method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.fadeInSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (isFadeInRunning() || (alpha == 1f && isVisible() && isFadeOutRunning().not())) {
        if (isFadeInRunning().not()) {
            onAnimationEnd?.invoke(false)
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
internal fun View.hideFadeOutSpring(
    stiffness: Float,
    hide: (() -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)?
) {
    if (isVisible().not() || isFadeOutRunning()) {
        if (isFadeOutRunning().not()) {
            onAnimationEnd?.invoke(false)
        }
        return
    }
    startFadeOutRunning()

    startFadeSpringAnimation(
        targetValue = 0f,
        stiffness = stiffness,
        onAnimationEnd = { canceled ->
            hide?.invoke()
            onAnimationEnd?.invoke(canceled)
        }
    )
}

fun View.fadeSpring(
    stiffness: Float = ANIMATION_STIFFNESS
) = spring(
    key = SpringAnimationPropertyKey.ALPHA,
    property = DynamicAnimation.ALPHA,
    stiffness = stiffness
)

fun View.startFadeSpringAnimation(
    targetValue: Float,
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null,
) = startSpringAnimation(
    key = SpringAnimationPropertyKey.ALPHA,
    property = DynamicAnimation.ALPHA,
    targetValue = targetValue,
    stiffness = stiffness,
    endListenerPair = R.string.alpha_end_listener_key to { canceled ->
        clearFadeInFadeOutRunning()
        onAnimationEnd?.invoke(canceled)
    }
)
