package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

/**
 * Animates the View visibility depending of the [visible] flag. If [visible] is true, the
 * [visibleFadeIn] is called, else the [goneFadeOut] is called.
 */
fun View.visibleOrGoneFade(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (visible) {
        visibleFadeIn(stiffness, dampingRatio, onAnimationEnd = onAnimationEnd)
    } else {
        goneFadeOut(stiffness, dampingRatio, onAnimationEnd = onAnimationEnd)
    }
}

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
fun View.goneFadeOut(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    fadeOutSpring(
        stiffness = stiffness,
        dampingRatio = dampingRatio
    ).startSpringAnimation { canceled ->
        gone()
        onAnimationEnd?.invoke(canceled)
    }
}

/**
 * Change the visibility to VISIBLE of the view using fade in animation. This method can be
 * reverted in the middle of the animation if the [goneFadeOut]
 * method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.visibleFadeIn(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (isVisible().not()) alpha = 0f
    visible()
    fadeInSpring(
        stiffness = stiffness,
        dampingRatio = dampingRatio
    ).startSpringAnimation(
        onAnimationEnd = onAnimationEnd
    )
}

fun JerryAnimationSet.fadeInSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = fadeSpring(
    targetValue = 1f,
    stiffness = stiffness,
    dampingRatio = dampingRatio
)

fun JerryAnimation.fadeInSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = fadeSpring(
    targetValue = 1f,
    stiffness = stiffness,
    dampingRatio = dampingRatio
)

fun View.fadeInSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = fadeSpring(
    targetValue = 1f,
    stiffness = stiffness,
    dampingRatio = dampingRatio
)

fun JerryAnimationSet.fadeOutSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = fadeSpring(
    targetValue = 0f,
    dampingRatio = dampingRatio,
    stiffness = stiffness
)

fun JerryAnimation.fadeOutSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = fadeSpring(
    targetValue = 0f,
    stiffness = stiffness,
    dampingRatio = dampingRatio
)

fun View.fadeOutSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = fadeSpring(
    targetValue = 0f,
    stiffness = stiffness,
    dampingRatio = dampingRatio
)

fun JerryAnimationSet.fadeSpring(
    targetValue: Float,
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio
)

fun JerryAnimation.fadeSpring(
    targetValue: Float,
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio
)

fun View.fadeSpring(
    targetValue: Float,
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio
)
