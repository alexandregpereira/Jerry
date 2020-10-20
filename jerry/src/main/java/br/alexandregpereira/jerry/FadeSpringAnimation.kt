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
    fadeOutSpring().force(
        stiffness = stiffness,
        dampingRatio = dampingRatio
    ).start { canceled ->
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
    fadeInSpring().force(
        stiffness = stiffness,
        dampingRatio = dampingRatio
    ).start(
        onAnimationEnd = onAnimationEnd
    )
}

fun JerryAnimationSet.fadeInSpring() = fadeSpring(
    targetValue = 1f
)

fun JerryAnimation.fadeInSpring() = fadeSpring(
    targetValue = 1f
)

fun View.fadeInSpring() = fadeSpring(
    targetValue = 1f
)

fun JerryAnimationSet.fadeOutSpring() = fadeSpring(
    targetValue = 0f
)

fun JerryAnimation.fadeOutSpring() = fadeSpring(
    targetValue = 0f
)

fun View.fadeOutSpring() = fadeSpring(
    targetValue = 0f
)

fun JerryAnimationSet.fadeSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    targetValue = targetValue
)

fun JerryAnimation.fadeSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    targetValue = targetValue
)

fun View.fadeSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ALPHA.id,
    property = DynamicAnimation.ALPHA,
    targetValue = targetValue
)
