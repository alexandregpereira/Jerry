package br.alexandregpereira.jerry.expandable

import android.view.View
import androidx.dynamicanimation.animation.SpringAnimation
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.force
import br.alexandregpereira.jerry.gone
import br.alexandregpereira.jerry.isVisible
import br.alexandregpereira.jerry.spring
import br.alexandregpereira.jerry.start
import br.alexandregpereira.jerry.visible

/**
 * Animates the View visibility depending of the [visible] flag. If [visible] is true, the
 * [visibleExpandHeight] is called, else the [goneCollapseHeight] is called.
 */
fun View.visibleOrGoneExpandableHeight(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (visible) {
        visibleExpandHeight(stiffness, onProgressChange, onAnimationEnd)
    } else {
        goneCollapseHeight(stiffness, onProgressChange, onAnimationEnd)
    }
}

/**
 * Animates the View visibility depending of the [visible] flag. If [visible] is true, the
 * [visibleExpandWidth] is called, else the [goneCollapseWidth] is called.
 */
fun View.visibleOrGoneExpandableWidth(
    visible: Boolean,
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (visible) {
        visibleExpandWidth(stiffness, onProgressChange, onAnimationEnd)
    } else {
        goneCollapseWidth(stiffness, onProgressChange, onAnimationEnd)
    }
}

/**
 * Animates collapsing the height and changes the visibility status to GONE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [visibleExpandHeight] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.goneCollapseHeight(
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = goneCollapse(
    stiffness = stiffness,
    isHeight = true,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates collapsing the width and changes the visibility status to GONE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [visibleExpandWidth] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.goneCollapseWidth(
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = goneCollapse(
    stiffness = stiffness,
    isHeight = false,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates expanding the height and changes the visibility status to VISIBLE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [goneCollapseHeight] method is called. Any alteration of the parent width during the this
 * animation makes glitches in the animation.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.visibleExpandHeight(
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = visibleExpand(
    stiffness = stiffness,
    isHeight = true,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates expanding the width and changes the visibility status to VISIBLE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [goneCollapseWidth] method is called. Any alteration of the parent width during the this
 * animation makes glitches in the animation.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.visibleExpandWidth(
    stiffness: Float = ANIMATION_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = visibleExpand(
    stiffness = stiffness,
    isHeight = false,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

internal fun View.goneCollapse(
    stiffness: Float = ANIMATION_STIFFNESS,
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (isVisible().not() || isCollapsingRunning()) {
        if (isCollapsingRunning().not()) {
            onAnimationEnd?.invoke(false)
        }
        return
    }
    startCollapsingRunning()
    getOrStoreWidthOrHeightOriginalValue(isHeight)
    expandCollapseSpring(
        targetValue = 0f,
        isHeight = isHeight,
        onProgressChange = onProgressChange,
    ).force(stiffness = stiffness).start { canceled ->
        gone()
        setLayoutParamSize(getOrStoreWidthOrHeightOriginalValue(isHeight), isHeight)
        finishExpandingCollapsingAnimation(isHeight, canceled, onAnimationEnd)
    }
}

internal fun View.visibleExpand(
    stiffness: Float = ANIMATION_STIFFNESS,
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (isExpandingRunning() || (isVisible() && isCollapsingRunning().not())) {
        if (isExpandingRunning().not()) {
            onAnimationEnd?.invoke(false)
        }
        return
    }

    val originalValue = getOrStoreWidthOrHeightOriginalValue(isHeight)
    val initialValue = (getLayoutParamSize(isHeight)).let {
        if (it == originalValue || it < 0) 0 else it
    }
    val targetValue = getTargetValue(originalValue, isHeight)

    if (targetValue == null) {
        finishExpandingCollapsingAnimation(
            isHeight,
            canceled = false,
            onAnimationEnd
        )
        return
    }
    startExpandingRunning()

    if (initialValue == 0) {
        if (isHeight) layoutParams.height = 1 else layoutParams.width = 1
    }
    visible()

    expandCollapseSpring(
        targetValue = targetValue.toFloat(),
        isHeight = isHeight,
        onProgressChange = onProgressChange
    ).force(stiffness = stiffness).start { canceled ->
        finishExpandingCollapsingAnimation(isHeight, canceled, onAnimationEnd)
    }
}

private fun View.expandCollapseSpring(
    targetValue: Float,
    isHeight: Boolean,
    onProgressChange: ((progress: Float) -> Unit)?,
) = spring(
    key = getExpandingCollapsingSpringKey(isHeight),
    property = widthHeightViewProperty(isHeight, onProgressChange),
    targetValue = targetValue
)
