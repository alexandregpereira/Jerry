package br.alexandregpereira.jerry

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.dynamicanimation.animation.SpringAnimation

/**
 * Animates expanding the height and changes the visibility status to VISIBLE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [collapseHeight] method is called. Any alteration of the parent width during the this animation
 * makes glitches in the animation.
 *
 * @param duration The duration of the animation.
 * @param onAnimationEnd The function to call when the animation is finished.
 */
fun View.expandHeight(
    duration: Long = ANIMATION_SHORT_TIME,
    onProgressChange: ((interpolatedTime: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = expand(
    duration,
    isHeight = true,
    onAnimationStart = null,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates expanding the width and changes the visibility status to VISIBLE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [collapseWidth] method is called. Any alteration of the parent width during the this animation
 * makes glitches in the animation.
 *
 * @param duration The duration of the animation.
 * @param onAnimationEnd The function to call when the animation is finished.
 */
fun View.expandWidth(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = expand(
    duration,
    isHeight = false,
    onAnimationStart = null,
    onProgressChange = null,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates collapsing the height and changes the visibility status to GONE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [expandHeight] method is called.
 *
 * @param duration The duration of the animation.
 * @param onAnimationEnd The function to call when the animation is finished.
 */
fun View.collapseHeight(
    duration: Long = ANIMATION_SHORT_TIME,
    onProgressChange: ((interpolatedTime: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = collapse(
    duration,
    isHeight = true,
    onAnimationStart = null,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

/**
 * Animates collapsing the width and changes the visibility status to GONE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [expandWidth] method is called.
 *
 * @param duration The duration of the animation.
 * @param onAnimationEnd The function to call when the animation is finished.
 */
fun View.collapseWidth(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = collapse(
    duration,
    isHeight = false,
    onAnimationStart = null,
    onProgressChange = null,
    onAnimationEnd = onAnimationEnd
)

internal fun View.collapse(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onProgressChange: ((interpolatedTime: Float) -> Unit)? = null,
    onAnimationStart: (() -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isVisible().not() || isCollapsingRunning()) {
        return
    }
    startCollapsingRunning()

    val originalValue = getOriginalValue(isHeight)
    val initialValue = getCollapsingInitialValue(isHeight)

    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                setLayoutParamSize(originalValue, isHeight)
            } else {
                val value = initialValue - (initialValue * interpolatedTime).toInt()
                val finalValue = if (value == 0) 1 else value

                setLayoutParamSize(finalValue, isHeight)
            }
            onProgressChange?.invoke(interpolatedTime)
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    animation.setAnimationListener(onEnd = {
        gone()
        finishExpandingCollapsingAnimation(onAnimationEnd)
    }, onStart = onAnimationStart)

    animation.duration = duration
    startAnimation(animation)
}

internal fun View.expand(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onProgressChange: ((interpolatedTime: Float) -> Unit)? = null,
    onAnimationStart: (() -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isExpandingRunning() || (isVisible() && isCollapsingRunning().not())) {
        return
    }

    val originalValue = getOriginalValue(isHeight)
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
    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val value = initialValue + ((targetValue - initialValue) * interpolatedTime).toInt()

            val finalValue = if (value == 0) 1 else {
                if (value == targetValue && originalValue == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    value
                }
            }

            setLayoutParamSize(finalValue, isHeight)
            onProgressChange?.invoke(interpolatedTime)
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    animation.setAnimationListener(onEnd = {
        finishExpandingCollapsingAnimation(onAnimationEnd)
    }, onStart = onAnimationStart)

    animation.duration = duration
    startAnimation(animation)
}

fun Animation.setAnimationListener(
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null
): Animation {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {
            onEnd?.invoke()
        }

        override fun onAnimationStart(animation: Animation?) {
            onStart?.invoke()
        }
    })

    return this
}

fun View.animateHeightVisibility(
    visible: Boolean,
    stiffness: Float = ANIMATION_FAST_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (visible) {
        expandHeightSpring(stiffness, onProgressChange, onAnimationEnd)
    } else {
        expandHeightSpring(stiffness, onProgressChange, onAnimationEnd)
    }
}

/**
 * Animates collapsing the height and changes the visibility status to GONE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [expandHeight] method is called.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_FAST_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.collapseHeightSpring(
    stiffness: Float = ANIMATION_FAST_STIFFNESS,
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
 * [ANIMATION_FAST_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.collapseWidthSpring(
    stiffness: Float = ANIMATION_FAST_STIFFNESS,
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
 * [ANIMATION_FAST_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.expandHeightSpring(
    stiffness: Float = ANIMATION_FAST_STIFFNESS,
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
 * if the [collapseWidth] method is called. Any alteration of the parent width during the this animation
 * makes glitches in the animation.
 *
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_FAST_STIFFNESS].
 * @param onAnimationEnd The function to call when the animation is finished.
 *
 * @see [SpringAnimation]
 */
fun View.expandWidthSpring(
    stiffness: Float = ANIMATION_FAST_STIFFNESS,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = expandSpring(
    stiffness = stiffness,
    isHeight = false,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

internal fun View.collapseSpring(
    stiffness: Float = ANIMATION_FAST_STIFFNESS,
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isVisible().not() || isCollapsingRunning()) {
        return
    }
    startCollapsingRunning()

    getOriginalValue(isHeight)
    getCollapsingInitialValue(isHeight)

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
    stiffness: Float = ANIMATION_FAST_STIFFNESS,
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isExpandingRunning() || (isVisible() && isCollapsingRunning().not())) {
        return
    }

    val originalValue = getOriginalValue(isHeight)
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
