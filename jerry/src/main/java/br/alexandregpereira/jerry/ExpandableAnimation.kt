package br.alexandregpereira.jerry

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 * Uses the [expand] and [visibleFadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseFading] method is called.
 *
 * @param duration The duration of the animation
 * @param isHeight Define if is the height or the width that is going to be animate
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.expandFading(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (alpha == 1f && (isVisible() && isCollapsingRunning().not())) {
        onAnimationEnd?.invoke()
        return
    }
    if (alpha == 1f) alpha = 0f

    if (alpha > 0f && alpha < 1f) {
        visibleFadeIn(duration = duration, onAnimationEnd = onAnimationEnd)
        return
    }

    expand(duration = duration / 2, isHeight = isHeight) {
        visibleFadeIn(duration = duration / 2, onAnimationEnd = onAnimationEnd)
    }
}

/**
 * Uses the [hideFadeOut] and [collapse] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandFading] method is called.
 *
 * @param duration The duration of the animation
 * @param isHeight Define if is going to animate the height or the width
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.collapseFading(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isExpandingRunning()) {
        collapse(duration = duration, isHeight = isHeight, onAnimationEnd = onAnimationEnd)
        return
    }

    hideFadeOut(duration = duration / 2) {
        collapse(duration = duration / 2, isHeight = isHeight, onAnimationEnd = onAnimationEnd)
    }
}

/**
 * Animates expanding the height or the width and changes the visibility status to VISIBLE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [collapse] method is called. The parent width needs to be match_parent or fixed
 * to work properly. Any alteration of the parent width during the this animation makes glitches
 * in the animation.
 *
 * @param duration The duration of the animation
 * @param isHeight Define if is going to animate the height or the width
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.expand(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) = expand(duration, isHeight, null, onAnimationEnd)

fun View.expand(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationStart: (() -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isExpandingRunning() || (isVisible() && isCollapsingRunning().not())) {
        onAnimationEnd?.invoke()
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

/**
 * Animates collapsing the height or the width and changes the visibility status to GONE.
 * This animation handles double click. This method can be reverted in the middle of the animation
 * if the [expand] method is called.
 *
 * @param duration The duration of the animation
 * @param isHeight Define if is going to animate the height or the width
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.collapse(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) = collapse(duration, isHeight, null, onAnimationEnd)

fun View.collapse(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationStart: (() -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (isVisible().not() || isCollapsingRunning()) {
        onAnimationEnd?.invoke()
        return
    }
    startCollapsingRunning()

    val originalValue = getOriginalValue(isHeight)
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

private fun View.finishExpandingCollapsingAnimation(onAnimationEnd: (() -> Unit)?) {
    clearOriginalValue()
    clearExpandingCollapsingRunning()
    onAnimationEnd?.invoke()
}

private fun View.setLayoutParamSize(value: Int, isHeight: Boolean) {
    if (isHeight) {
        layoutParams.height = value
    } else {
        layoutParams.width = value
    }
}

private fun View.getLayoutParamSize(isHeight: Boolean): Int {
    return if (isHeight) layoutParams.height else layoutParams.width
}


private fun View.isExpandingCollapsingRunning(animationMode: Int): Boolean =
    isAnimationRunning(R.string.is_expanding_collapsing_key, animationMode)

private fun View.setExpandingCollapsingRunning(animationMode: Int) =
    setAnimationRunning(R.string.is_expanding_collapsing_key, animationMode)

fun View.isExpandingRunning() = isExpandingCollapsingRunning(ENTER_ANIMATION_MODE)

fun View.isCollapsingRunning() = isExpandingCollapsingRunning(POP_ANIMATION_MODE)

private fun View.clearExpandingCollapsingRunning() =
    setExpandingCollapsingRunning(NONE_ANIMATION_MODE)

private fun View.startExpandingRunning() = setExpandingCollapsingRunning(ENTER_ANIMATION_MODE)

private fun View.startCollapsingRunning() = setExpandingCollapsingRunning(POP_ANIMATION_MODE)

private fun View.getCollapsingInitialValue(isHeight: Boolean): Int {
    val value = getLayoutParamSize(isHeight)
    return value.takeIf { it > 0 } ?: run {
        if (isHeight) measuredHeight else measuredWidth
    }
}

private fun View.getOriginalValue(isHeight: Boolean): Int {
    return runCatching {
        getTag(id) as Int
    }.getOrElse {
        (getLayoutParamSize(isHeight)).apply {
            setTag(id, this)
        }
    }
}

private fun View.clearOriginalValue() {
    runCatching { setTag(id, null) }
}

private fun View.getTargetValue(originalValue: Int, isHeight: Boolean): Int? {
    if (originalValue > 0) return originalValue

    val parentSize = (parent as View).width
    if (parentSize == 0) {
        visible()
        return null
    }

    if (originalValue == ViewGroup.LayoutParams.MATCH_PARENT) return parentSize

    val widthMeasureSize: Int
    val heightMeasureSize: Int

    val widthMeasureSpecMode: Int
    val heightMeasureSpecMode: Int

    if (isHeight) {
        widthMeasureSize = parentSize
        heightMeasureSize = 0

        widthMeasureSpecMode = View.MeasureSpec.EXACTLY
        heightMeasureSpecMode = View.MeasureSpec.UNSPECIFIED
    } else {
        widthMeasureSize = 0
        heightMeasureSize = parentSize

        widthMeasureSpecMode = View.MeasureSpec.UNSPECIFIED
        heightMeasureSpecMode = View.MeasureSpec.EXACTLY
    }

    val widthMeasureSpec =
        View.MeasureSpec.makeMeasureSpec(widthMeasureSize, widthMeasureSpecMode)

    val heightMeasureSpec =
        View.MeasureSpec.makeMeasureSpec(heightMeasureSize, heightMeasureSpecMode)

    measure(widthMeasureSpec, heightMeasureSpec)

    return if (isHeight) measuredHeight else measuredWidth
}
