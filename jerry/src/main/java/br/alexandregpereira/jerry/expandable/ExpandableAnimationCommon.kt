package br.alexandregpereira.jerry.expandable

import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import br.alexandregpereira.jerry.AnimationMode
import br.alexandregpereira.jerry.OriginalValueKey
import br.alexandregpereira.jerry.R
import br.alexandregpereira.jerry.SpringAnimationPropertyKey
import br.alexandregpereira.jerry.isAnimationRunning
import br.alexandregpereira.jerry.setAnimationRunning
import br.alexandregpereira.jerry.visible

internal fun getExpandingCollapsingSpringKey(isHeight: Boolean): Int {
    return if (isHeight) {
        SpringAnimationPropertyKey.HEIGHT.id
    } else {
        SpringAnimationPropertyKey.WIDTH.id
    }
}

internal fun getExpandingCollapsingEndListenerKey(isHeight: Boolean): Int {
    return if (isHeight) {
        R.string.expanding_collapsing_height_end_listener_key
    } else {
        R.string.expanding_collapsing_width_end_listener_key
    }
}

internal fun View.finishExpandingCollapsingAnimation(
    isHeight: Boolean,
    onAnimationEnd: (() -> Unit)?
) {
    clearWidthOrHeightOriginalValue(isHeight)
    clearExpandingCollapsingRunning()
    onAnimationEnd?.invoke()
}

internal fun View.finishExpandingCollapsingAnimation(
    isHeight: Boolean,
    canceled: Boolean,
    onAnimationEnd: ((canceled: Boolean) -> Unit)?
) {
    clearWidthOrHeightOriginalValue(isHeight)
    clearExpandingCollapsingRunning()
    onAnimationEnd?.invoke(canceled)
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


internal fun View.isExpandingCollapsingRunning(animationMode: AnimationMode): Boolean =
    isAnimationRunning(R.string.is_expanding_collapsing_key, animationMode)

internal fun View.setExpandingCollapsingRunning(animationMode: AnimationMode) =
    setAnimationRunning(R.string.is_expanding_collapsing_key, animationMode)

internal fun View.isExpandingRunning() =
    isExpandingCollapsingRunning(AnimationMode.ENTER_ANIMATION_MODE)

internal fun View.isCollapsingRunning() =
    isExpandingCollapsingRunning(AnimationMode.POP_ANIMATION_MODE)

internal fun View.clearExpandingCollapsingRunning() =
    setExpandingCollapsingRunning(AnimationMode.NONE_ANIMATION_MODE)

internal fun View.startExpandingRunning() =
    setExpandingCollapsingRunning(AnimationMode.ENTER_ANIMATION_MODE)

internal fun View.startCollapsingRunning() =
    setExpandingCollapsingRunning(AnimationMode.POP_ANIMATION_MODE)

internal fun View.getCollapsingInitialValue(isHeight: Boolean): Int {
    val value = getLayoutParamSize(isHeight)
    return value.takeIf { it > 0 } ?: run {
        if (isHeight) measuredHeight else measuredWidth
    }
}

internal fun View.getOrStoreWidthOrHeightOriginalValue(isHeight: Boolean): Int {
    val key = getWidthOrHeightOriginalValueKey(isHeight)
    return runCatching {
        getTag(key) as Int
    }.getOrElse {
        (getLayoutParamSize(isHeight)).apply {
            setTag(key, this)
        }
    }
}

internal fun getWidthOrHeightOriginalValueKey(isHeight: Boolean): Int {
    return if (isHeight) {
        OriginalValueKey.HEIGHT.id
    } else {
        OriginalValueKey.WIDTH.id
    }
}

internal fun View.clearWidthOrHeightOriginalValue(isHeight: Boolean) {
    runCatching {
        setTag(getWidthOrHeightOriginalValueKey(isHeight), null)
    }
}

internal fun View.getTargetValue(originalValue: Int, isHeight: Boolean): Int? {
    if (originalValue > 0) return originalValue

    val parentSize = (parent as View).width
    if (parentSize == 0) {
        visible()
        return null
    }

    if (originalValue == ViewGroup.LayoutParams.MATCH_PARENT || originalValue == 0) {
        return parentSize
    }

    measure(
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
    )

    return if (isHeight) measuredHeight else measuredWidth
}


