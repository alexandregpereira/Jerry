package br.alexandregpereira.jerry

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation

/**
 * Uses the [expandHeight] and [visibleFadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseHeightFading] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.expandHeightFading(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = expandFading(duration, isHeight = true, onAnimationEnd)

/**
 * Uses the [expandWidth] and [visibleFadeIn] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [collapseWidthFading] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.expandWidthFading(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = expandFading(duration, isHeight = false, onAnimationEnd)

private fun View.expandFading(
    duration: Long = ANIMATION_SHORT_TIME,
    isHeight: Boolean = true,
    onAnimationEnd: (() -> Unit)? = null
) {
    if (alpha == 1f && (isVisible() && isCollapsingRunning().not())) {
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
 * Uses the [hideFadeOut] and [collapseHeight] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandHeightFading] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.collapseHeightFading(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = collapseFading(duration, isHeight = true, onAnimationEnd)

/**
 * Uses the [hideFadeOut] and [collapseWidth] animations in sequence. This animation
 * handles double click. This method can be reverted in the middle of the animation if the
 * [expandWidthFading] method is called.
 *
 * @param duration The duration of the animation
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun View.collapseWidthFading(
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null
) = collapseFading(duration, isHeight = false, onAnimationEnd)

private fun View.collapseFading(
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

private fun View.viewProperty(
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null
) = object : FloatPropertyCompat<View>("viewProperty") {

    private val originalValue: Int
        get() = getOriginalValue(isHeight)

    private val initialValue: Int
        get() = if (isExpandingRunning()) {
            (getLayoutParamSize(isHeight)).let {
                if (it == originalValue || it < 0) 0 else it
            }
        } else {
            getCollapsingInitialValue(isHeight)
        }

    private val targetValue: Int?
        get() = if (isExpandingRunning()) {
            getTargetValue(originalValue, isHeight)
        } else {
            null
        }

    override fun getValue(view: View?): Float {
        return initialValue.toFloat().apply {
            Log.d("heightViewProperty", "get: $this")
        }
    }

    override fun setValue(view: View?, value: Float) {
        Log.d("heightViewProperty", "set: $value")
        val targetValue = targetValue
        if (targetValue != null) {
            setExpandingValue(value, targetValue)
        } else {
            setCollapsingValue(value)
        }

        onProgressChange?.invoke(progressFunction(value))
        requestLayout()
    }

    private val progressFunction = createProgressFunction()

    private fun createProgressFunction(): (Float) -> Float {
        val viewSize = targetValue ?: initialValue
        return if (isExpandingRunning()) {
            { value ->
                (viewSize - (viewSize - value)) / viewSize
            }
        } else {
            { value ->
                (viewSize - value) / viewSize
            }
        }
    }

    private fun setCollapsingValue(value: Float) {
        if (value == 0f) {
            setLayoutParamSize(originalValue, isHeight)
        } else {
            setLayoutParamSize(value.toInt(), isHeight)
        }
    }

    private fun setExpandingValue(value: Float, targetValue: Int) {
        val finalValue = if (value == 0f) 1f else {
            if (value == targetValue.toFloat() &&
                originalValue == ViewGroup.LayoutParams.WRAP_CONTENT
            ) {
                ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                value
            }
        }

        setLayoutParamSize(finalValue.toInt(), isHeight)
    }
}

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

private fun View.collapseSpring(
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
    val initialValue = getCollapsingInitialValue(isHeight)

    startExpandCollapseAnimation(
        stiffness = stiffness,
        isHeight = isHeight,
        targetValue = 0f,
        onProgressChange = onProgressChange,
        onAnimationEnd = {
            gone()
            onAnimationEnd?.invoke()
        }
    )
}

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

private fun View.expandSpring(
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

    startExpandCollapseAnimation(
        stiffness = stiffness,
        isHeight = isHeight,
        targetValue = targetValue.toFloat(),
        onProgressChange = onProgressChange,
        onAnimationEnd = onAnimationEnd
    )
}

private fun View.startExpandCollapseAnimation(
    stiffness: Float,
    isHeight: Boolean = true,
    targetValue: Float,
    onProgressChange: ((progress: Float) -> Unit)?,
    onAnimationEnd: (() -> Unit)?
) {
    val springAnimation = this.spring(
        getExpandingCollapsingSpringKey(isHeight),
        viewProperty(isHeight, onProgressChange),
        stiffness = stiffness
    )

    getExpandingCollapsingEndListener(isHeight)?.also {
        springAnimation.removeEndListener(it)
    }
    springAnimation.addExpandingCollapsingEndListener(view = this, isHeight, onAnimationEnd)

    springAnimation.animateToFinalPosition(targetValue)
}

private fun SpringAnimation.addExpandingCollapsingEndListener(
    view: View,
    isHeight: Boolean,
    onAnimationEnd: (() -> Unit)? = null
) {
    DynamicAnimation.OnAnimationEndListener { _, _, _, _ ->
        view.getExpandingCollapsingEndListener(isHeight)?.let {
            this.removeEndListener(it)
        }
        view.finishExpandingCollapsingAnimation(onAnimationEnd)
    }.let {
        this.addEndListener(it)
        view.setTag(getExpandingCollapsingEndListenerKey(isHeight), it)
    }
}

private fun View.getExpandingCollapsingEndListener(isHeight: Boolean): DynamicAnimation.OnAnimationEndListener? {
    return getTag(getExpandingCollapsingEndListenerKey(isHeight)).run {
        this as? DynamicAnimation.OnAnimationEndListener
    }
}

private fun getExpandingCollapsingSpringKey(isHeight: Boolean): Int {
    return if (isHeight) {
        R.string.expanding_collapsing_height_spring_key
    } else {
        R.string.expanding_collapsing_width_spring_key
    }
}

private fun getExpandingCollapsingEndListenerKey(isHeight: Boolean): Int {
    return if (isHeight) {
        R.string.expanding_collapsing_height_end_listener_key
    } else {
        R.string.expanding_collapsing_width_end_listener_key
    }
}

private fun View.collapse(
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

private fun View.expand(
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
    val key = getOriginalValueKey(isHeight)
    return runCatching {
        getTag(key) as Int
    }.getOrElse {
        (getLayoutParamSize(isHeight)).apply {
            setTag(key, this)
        }
    }
}

private fun getOriginalValueKey(isHeight: Boolean): Int {
    return if (isHeight) {
        R.string.expanding_collapsing_height_original_value_key
    } else {
        R.string.expanding_collapsing_width_original_value_key
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
