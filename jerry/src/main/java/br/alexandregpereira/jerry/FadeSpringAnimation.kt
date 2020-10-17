package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation

/**
 * Animates the View visibility depending of the [visible] flag. If [visible] is true, the
 * [fadeInSpring] is called, else the [fadeOutSpring] is called.
 */
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
 * reverted in the middle of the animation if the [fadeInSpring] method is called.
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
    fadeSpring(stiffness = stiffness).startFadeOutSpringAnimation(
        onAnimationEnd = { canceled ->
            gone()
            onAnimationEnd?.invoke(canceled)
        }
    )
}

/**
 * Change the visibility to VISIBLE of the view using fade in animation. This method can be
 * reverted in the middle of the animation if the [fadeOutSpring]
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
    if (isVisible().not()) alpha = 0f
    visible()
    fadeSpring(stiffness = stiffness).startFadeInSpringAnimation(
        onAnimationEnd = onAnimationEnd
    )
}

fun JerryAnimation.startFadeOutSpringAnimation(
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = startFadeSpringAnimation(
    targetValue = 0f,
    onAnimationEnd = { canceled ->
        onAnimationEnd?.invoke(canceled)
    }
)

fun JerryAnimation.startFadeInSpringAnimation(
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = startFadeSpringAnimation(
    targetValue = 1f,
    onAnimationEnd = onAnimationEnd
)

fun JerryAnimation.targetFadeIn() = target(1f)

fun JerryAnimation.targetFadeOut() = target(0f)

fun JerryAnimationSet.targetFadeIn() = target(1f)

fun JerryAnimationSet.targetFadeOut() = target(0f)

fun JerryAnimationSet.fadeSpring(
    stiffness: Float = ANIMATION_STIFFNESS
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    stiffness = stiffness
)

fun JerryAnimation.fadeSpring(
    stiffness: Float = ANIMATION_STIFFNESS
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    stiffness = stiffness
)

fun View.fadeSpring(
    stiffness: Float = ANIMATION_STIFFNESS
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    stiffness = stiffness
)

fun JerryAnimation.startFadeSpringAnimation(
    targetValue: Float,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null,
) = startSpringAnimation(
    targetValue = targetValue,
    onAnimationEnd = onAnimationEnd
)
