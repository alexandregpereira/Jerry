package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation

fun View.animateHeightFadingVisibility(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (visible) {
        expandHeightFadingSpring(stiffness, onAnimationEnd)
    } else {
        collapseHeightFadingSpring(stiffness, onAnimationEnd)
    }
}

fun View.animateWidthFadingVisibility(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (visible) {
        expandWidthFadingSpring(stiffness, onAnimationEnd)
    } else {
        collapseWidthFadingSpring(stiffness, onAnimationEnd)
    }
}

/**
 * Uses the [expandHeight] and [fadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseHeightFading] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [SpringAnimation]
 */
fun View.expandHeightFadingSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: (() -> Unit)? = null
) = expandFadingSpring(stiffness, isHeight = true, onAnimationEnd)

/**
 * Uses the [expandWidth] and [fadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseWidthFading] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [SpringAnimation]
 */
fun View.expandWidthFadingSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: (() -> Unit)? = null
) = expandFadingSpring(stiffness, isHeight = false, onAnimationEnd)

/**
 * Uses the [hideFadeOut] and [collapseHeight] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandHeightFading] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [SpringAnimation]
 */
fun View.collapseHeightFadingSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: (() -> Unit)? = null
) = collapseFadingSpring(stiffness, isHeight = true, onAnimationEnd)

/**
 * Uses the [hideFadeOut] and [collapseWidth] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandWidthFading] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [SpringAnimation]
 */
fun View.collapseWidthFadingSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: (() -> Unit)? = null
) = collapseFadingSpring(stiffness, isHeight = false, onAnimationEnd)

private fun View.collapseFadingSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isExpandingRunning()) {
        collapseSpring(isHeight = isHeight, onAnimationEnd = onAnimationEnd)
        return
    }

    hideFadeOutSpring(stiffness = stiffness * 2f) {
        collapseSpring(
            stiffness = stiffness * 2f,
            isHeight = isHeight,
            onAnimationEnd = onAnimationEnd
        )
    }
}

private fun View.expandFadingSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (alpha == 1f && (isVisible() && isCollapsingRunning().not())) {
        return
    }
    if (alpha == 1f) alpha = 0f

    if (alpha > 0f && alpha < 1f) {
        fadeInSpring(onAnimationEnd = onAnimationEnd)
        return
    }

    expandSpring(stiffness = stiffness * 2f, isHeight = isHeight) {
        fadeInSpring(stiffness = stiffness * 2f, onAnimationEnd = onAnimationEnd)
    }
}
