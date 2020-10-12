package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation

fun View.animateHeightVisibility(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (visible) {
        expandHeightSpring(stiffness, onProgressChange, onAnimationEnd)
    } else {
        collapseHeightSpring(stiffness, onProgressChange, onAnimationEnd)
    }
}

fun View.animateWidthVisibility(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (visible) {
        expandWidthSpring(stiffness, onProgressChange, onAnimationEnd)
    } else {
        collapseWidthSpring(stiffness, onProgressChange, onAnimationEnd)
    }
}

/**
 * Animates collapsing the height and changes the visibility status to GONE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [expandHeight] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.collapseHeightSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = collapseSpring(
    stiffness = stiffness,
    isHeight = true,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates collapsing the width and changes the visibility status to GONE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [expandWidth] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.collapseWidthSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = collapseSpring(
    stiffness = stiffness,
    isHeight = false,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates expanding the height and changes the visibility status to VISIBLE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [collapseHeight] method is called. Any alteration of the parent width during the this
 * animation makes glitches in the animation.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.expandHeightSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = expandSpring(
    stiffness = stiffness,
    isHeight = true,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates expanding the width and changes the visibility status to VISIBLE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [collapseWidth] method is called. Any alteration of the parent width during the this
 * animation makes glitches in the animation.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.expandWidthSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = expandSpring(
    stiffness = stiffness,
    isHeight = false,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

internal fun View.collapseSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isVisible().not() || isCollapsingRunning()) {
        return
    }
    startCollapsingRunning()
    getOrStoreWidthOrHeightOriginalValue(isHeight)
    startExpandCollapseSpringAnimation(
        targetValue = 0f,
        stiffness = stiffness,
        isHeight = isHeight,
        onProgressChange = onProgressChange,
        onAnimationEnd = {
            gone()
            onAnimationEnd?.invoke()
        }
    )
}

internal fun View.expandSpring(
    stiffness: Float = ANIMATION_STIFFNESS,
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isExpandingRunning() || (isVisible() && isCollapsingRunning().not())) {
        return
    }

    val originalValue = getOrStoreWidthOrHeightOriginalValue(isHeight)
    val initialValue = (getLayoutParamSize(isHeight)).let {
        if (it == originalValue || it < 0) 0 else it
    }
    val targetValue = getTargetValue(originalValue, isHeight)

    if (targetValue == null) {
        finishExpandingCollapsingAnimation(onAnimationEnd)
        return
    }
    startExpandingRunning()

    if (initialValue == 0) {
        if (isHeight) layoutParams.height = 1 else layoutParams.width = 1
    }
    visible()

    startExpandCollapseSpringAnimation(
        targetValue = targetValue.toFloat(),
        stiffness = stiffness,
        isHeight = isHeight,
        onProgressChange = onProgressChange,
        onAnimationEnd = onAnimationEnd
    )
}

private fun View.startExpandCollapseSpringAnimation(
    targetValue: Float,
    stiffness: Float,
    isHeight: Boolean,
    onProgressChange: ((progress: Float) -> Unit)?,
    onAnimationEnd: (() -> Unit)?,
) = startSpringAnimation(
    key = getExpandingCollapsingSpringKey(isHeight),
    property = widthHeightViewProperty(isHeight, onProgressChange),
    targetValue = targetValue,
    stiffness = stiffness,
    endListenerPair = getExpandingCollapsingEndListenerKey(isHeight) to {
        finishExpandingCollapsingAnimation(onAnimationEnd)
    }
)
