package br.alexandregpereira.jerry.expandable

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import br.alexandregpereira.jerry.ANIMATION_SHORT_TIME
import br.alexandregpereira.jerry.gone
import br.alexandregpereira.jerry.isVisible
import br.alexandregpereira.jerry.visible

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
    onProgressChange: ((interpolatedTime: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = expand(
    duration,
    isHeight = false,
    onProgressChange = onProgressChange,
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
    onProgressChange: ((interpolatedTime: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) = collapse(
    duration,
    isHeight = false,
    onProgressChange = onProgressChange,
    onAnimationEnd = onAnimationEnd
)

internal fun View.collapse(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onProgressChange: ((interpolatedTime: Float) -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isVisible().not() || isCollapsingRunning()) {
        return
    }
    startCollapsingRunning()

    val originalValue = getOrStoreWidthOrHeightOriginalValue(isHeight)
    val initialValue = getCollapsingInitialValue(isHeight)

    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                gone()
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
    animation.setAnimationListener(
        onEnd = {
            finishExpandingCollapsingAnimation(isHeight, onAnimationEnd)
        }
    )

    animation.duration = duration
    startAnimation(animation)
}

internal fun View.expand(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onProgressChange: ((interpolatedTime: Float) -> Unit)? = null,
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
        finishExpandingCollapsingAnimation(isHeight, onAnimationEnd)
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
    animation.setAnimationListener(
        onEnd = {
            finishExpandingCollapsingAnimation(isHeight, onAnimationEnd)
        }
    )

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
