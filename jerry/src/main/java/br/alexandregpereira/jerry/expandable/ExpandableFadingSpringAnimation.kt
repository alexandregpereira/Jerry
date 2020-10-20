package br.alexandregpereira.jerry.expandable

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.fadeOutSpring
import br.alexandregpereira.jerry.visibleFadeIn
import br.alexandregpereira.jerry.goneFadeOut
import br.alexandregpereira.jerry.fadeSpring
import br.alexandregpereira.jerry.isVisible
import br.alexandregpereira.jerry.start

/**
 * Animates the View visibility depending of the [visible] flag. If [visible] is true, the
 * [visibleExpandHeightFadeIn] is called, else the [goneCollapseHeightFadeOut] is called.
 */
fun View.visibleOrGoneExpandableHeightFade(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (visible) {
        visibleExpandHeightFadeIn(stiffness, dampingRatio, onAnimationEnd)
    } else {
        goneCollapseHeightFadeOut(stiffness, dampingRatio, onAnimationEnd)
    }
}

/**
 * Animates the View visibility depending of the [visible] flag. If [visible] is true, the
 * [visibleExpandWidthFadeIn] is called, else the [goneCollapseWidthFadeOut] is called.
 */
fun View.visibleOrGoneExpandableWidthFade(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (visible) {
        visibleExpandWidthFadeIn(stiffness, dampingRatio, onAnimationEnd)
    } else {
        goneCollapseWidthFadeOut(stiffness, dampingRatio, onAnimationEnd)
    }
}

/**
 * Uses the [visibleExpandHeight] and [visibleFadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [goneCollapseHeightFadeOut] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [SpringAnimation]
 */
fun View.visibleExpandHeightFadeIn(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = visibleExpandFadeIn(stiffness, dampingRatio, isHeight = true, onAnimationEnd = onAnimationEnd)

/**
 * Uses the [visibleExpandWidth] and [visibleFadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [goneCollapseWidthFadeOut] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [SpringAnimation]
 */
fun View.visibleExpandWidthFadeIn(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = visibleExpandFadeIn(stiffness, dampingRatio, isHeight = false, onAnimationEnd = onAnimationEnd)

/**
 * Uses the [goneFadeOut] and [goneCollapseHeight] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [visibleExpandHeightFadeIn] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [SpringAnimation]
 */
fun View.goneCollapseHeightFadeOut(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = goneCollapseFadeOut(stiffness, dampingRatio, isHeight = true, onAnimationEnd = onAnimationEnd)

/**
 * Uses the [fadeSpring] and [goneCollapseWidth] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [visibleExpandWidthFadeIn] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished
 *
 * @see [SpringAnimation]
 */
fun View.goneCollapseWidthFadeOut(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = goneCollapseFadeOut(stiffness, dampingRatio, isHeight = false, onAnimationEnd = onAnimationEnd)

private fun View.goneCollapseFadeOut(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    isHeight: Boolean = true,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    fadeOutSpring(stiffness = stiffness * 2f, dampingRatio).start {
        goneCollapse(
            stiffness = stiffness * 2f,
            isHeight = isHeight,
            onAnimationEnd = onAnimationEnd
        )
    }
}

private fun View.visibleExpandFadeIn(
    stiffness: Float = ANIMATION_STIFFNESS,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    isHeight: Boolean = true,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (isVisible().not()) alpha = 0f
    visibleExpand(stiffness = stiffness * 2f, isHeight = isHeight) {
        visibleFadeIn(stiffness = stiffness * 2f, dampingRatio, onAnimationEnd)
    }
}
