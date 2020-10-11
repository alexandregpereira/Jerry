package br.alexandregpereira.jerry

import android.view.View
import android.view.ViewGroup

internal fun getExpandingCollapsingSpringKey(isHeight: Boolean): Int {
    return if (isHeight) {
        R.string.expanding_collapsing_height_spring_key
    } else {
        R.string.expanding_collapsing_width_spring_key
    }
}

internal fun getExpandingCollapsingEndListenerKey(isHeight: Boolean): Int {
    return if (isHeight) {
        R.string.expanding_collapsing_height_end_listener_key
    } else {
        R.string.expanding_collapsing_width_end_listener_key
    }
}

internal fun View.finishExpandingCollapsingAnimation(onAnimationEnd: (() -> Unit)?) {
    clearOriginalValue()
    clearExpandingCollapsingRunning()
    onAnimationEnd?.invoke()
}

internal fun View.setLayoutParamSize(value: Int, isHeight: Boolean) {
    if (isHeight) {
        layoutParams.height = value
    } else {
        layoutParams.width = value
    }
}

internal fun View.getLayoutParamSize(isHeight: Boolean): Int {
    return if (isHeight) layoutParams.height else layoutParams.width
}


internal fun View.isExpandingCollapsingRunning(animationMode: Int): Boolean =
    isAnimationRunning(R.string.is_expanding_collapsing_key, animationMode)

internal fun View.setExpandingCollapsingRunning(animationMode: Int) =
    setAnimationRunning(R.string.is_expanding_collapsing_key, animationMode)

internal fun View.isExpandingRunning() = isExpandingCollapsingRunning(ENTER_ANIMATION_MODE)

internal fun View.isCollapsingRunning() = isExpandingCollapsingRunning(POP_ANIMATION_MODE)

internal fun View.clearExpandingCollapsingRunning() =
    setExpandingCollapsingRunning(NONE_ANIMATION_MODE)

internal fun View.startExpandingRunning() = setExpandingCollapsingRunning(ENTER_ANIMATION_MODE)

internal fun View.startCollapsingRunning() = setExpandingCollapsingRunning(POP_ANIMATION_MODE)

internal fun View.getCollapsingInitialValue(isHeight: Boolean): Int {
    val value = getLayoutParamSize(isHeight)
    return value.takeIf { it > 0 } ?: run {
        if (isHeight) measuredHeight else measuredWidth
    }
}

internal fun View.getOriginalValue(isHeight: Boolean): Int {
    val key = getOriginalValueKey(isHeight)
    return runCatching {
        getTag(key) as Int
    }.getOrElse {
        (getLayoutParamSize(isHeight)).apply {
            setTag(key, this)
        }
    }
}

internal fun getOriginalValueKey(isHeight: Boolean): Int {
    return if (isHeight) {
        R.string.expanding_collapsing_height_original_value_key
    } else {
        R.string.expanding_collapsing_width_original_value_key
    }
}

internal fun View.clearOriginalValue() {
    runCatching { setTag(id, null) }
}

internal fun View.getTargetValue(originalValue: Int, isHeight: Boolean): Int? {
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


