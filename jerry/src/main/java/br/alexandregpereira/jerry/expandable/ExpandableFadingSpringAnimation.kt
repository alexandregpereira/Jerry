package br.alexandregpereira.jerry.expandable

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.fadeInSpring
import br.alexandregpereira.jerry.fadeOutSpring
import br.alexandregpereira.jerry.hideFadeOutSpring
import br.alexandregpereira.jerry.isVisible

/**
 * Animates the View visibility depending of the [visible] flag. If [visible] is true, the
 * [expandHeightFadingSpring] is called, else the [collapseHeightFadingSpring] is called.
 */
fun View.animateHeightFadingVisibility(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (visible) {
        expandHeightFadingSpring(stiffness, onAnimationEnd)
    } else {
        collapseHeightFadingSpring(stiffness, onAnimationEnd)
    }
}

/**
 * Animates the View visibility depending of the [visible] flag. If [visible] is true, the
 * [expandWidthFadingSpring] is called, else the [collapseWidthFadingSpring] is called.
 */
fun View.animateWidthFadingVisibility(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (visible) {
        expandWidthFadingSpring(stiffness, onAnimationEnd)
    } else {
        collapseWidthFadingSpring(stiffness, onAnimationEnd)
    }
}

/**
 * Uses the [expandHeightSpring] and [fadeInSpring] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseHeightFadingSpring] method is called.
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
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = expandFadingSpring(stiffness, isHeight = true, onAnimationEnd = onAnimationEnd)

/**
 * Uses the [expandWidthSpring] and [fadeInSpring] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseWidthFadingSpring] method is called.
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
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = expandFadingSpring(stiffness, isHeight = false, onAnimationEnd = onAnimationEnd)

/**
 * Uses the [fadeOutSpring] and [collapseHeightSpring] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandHeightFadingSpring] method is called.
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
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = collapseFadingSpring(stiffness, isHeight = true, onAnimationEnd = onAnimationEnd)

/**
 * Uses the [hideFadeOutSpring] and [collapseWidthSpring] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandWidthFadingSpring] method is called.
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
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = collapseFadingSpring(stiffness, isHeight = false, onAnimationEnd = onAnimationEnd)

private fun View.collapseFadingSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    isHeight: Boolean = true,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
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
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
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
