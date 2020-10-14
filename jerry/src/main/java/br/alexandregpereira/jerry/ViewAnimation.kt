package br.alexandregpereira.jerry

import android.view.View
import android.view.ViewPropertyAnimator
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

const val ANIMATION_SHORT_TIME = 200L

const val ANIMATION_STIFFNESS = 600f

enum class AnimationMode {
    /**
     * Used to clear the key to verify if a animation is running.
     */
    NONE_ANIMATION_MODE,
    /**
     * Used to identify if the animation that is running is an enter animation.
     */
    ENTER_ANIMATION_MODE,
    /**
     * Used to identify if the animation that is running is an pop animation.
     */
    POP_ANIMATION_MODE
}

fun View.cancelSpringAnimation() {
    SpringAnimationPropertyKey.values().forEach {
        getSpringAnimation(it)?.cancel()
    }
}

fun View.skipToEndSpringAnimation() {
    SpringAnimationPropertyKey.values().forEach { property ->
        getSpringAnimation(property)?.takeIf { it.canSkipToEnd() }?.skipToEnd()
    }
}

fun View.spring(
    key: SpringAnimationPropertyKey,
    property: FloatPropertyCompat<View>,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    stiffness: Float = SpringForce.STIFFNESS_LOW
): SpringAnimation {
    var springAnimation = getSpringAnimation(key)
    if (springAnimation == null) {
        springAnimation = SpringAnimation(this, property).apply {
            spring = SpringForce().apply {
                this.dampingRatio = dampingRatio
                this.stiffness = stiffness
            }
        }
        setTag(key.id, springAnimation)
    }
    return springAnimation
}

fun View.startSpringAnimation(
    key: SpringAnimationPropertyKey,
    property: FloatPropertyCompat<View>,
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    endListenerPair: Pair<Int, (canceled: Boolean) -> Unit>? = null
) {
    val springAnimation = this.spring(
        key,
        property,
        stiffness = stiffness,
        dampingRatio = dampingRatio
    )

    endListenerPair?.let {
        getSpringEndListener(key = it.first)?.also { endListener ->
            springAnimation.removeEndListener(endListener)
        }
        springAnimation.addSpringEndListener(
            key = it.first,
            view = this,
            onAnimationEnd = it.second
        )
    }

    springAnimation.animateToFinalPosition(targetValue)
}

internal fun SpringAnimation.addSpringEndListener(
    key: Int,
    view: View,
    onAnimationEnd: (canceled: Boolean) -> Unit
) {
    DynamicAnimation.OnAnimationEndListener { animation, canceled, _, _ ->
        view.getSpringEndListener(key)?.let {
            animation.removeEndListener(it)
        }
        onAnimationEnd(canceled)
    }.let {
        this.addEndListener(it)
        view.setTag(key, it)
    }
}

fun View.getSpringAnimation(key: SpringAnimationPropertyKey): SpringAnimation? {
    return getTag(key.id) as? SpringAnimation
}

internal fun View.getSpringEndListener(key: Int): DynamicAnimation.OnAnimationEndListener? {
    return getTag(key).run {
        this as? DynamicAnimation.OnAnimationEndListener
    }
}

/**
 * Check if is animation is running using the view tag system.
 *
 * @param key The key of the tag must be a string resource id.
 * @param animationMode The value to be store at the key. Must be [AnimationMode.NONE_ANIMATION_MODE],
 * [AnimationMode.ENTER_ANIMATION_MODE] or [AnimationMode.POP_ANIMATION_MODE].
 */
internal fun View.isAnimationRunning(key: Int, animationMode: AnimationMode): Boolean {
    return runCatching {
        getTag(key) as Int
    }.getOrElse {
        0
    } == animationMode.ordinal
}

/**
 * Check if a [SpringAnimation] is running using the view tag system. Unlike [isAnimationRunning],
 * this function do not distinguishes if is an enter animation or the pop animation is running.
 *
 * @param key The key of the tag must be a string resource id.
 */
internal fun View.isSpringAnimationRunning(key: Int): Boolean {
    val springAnimation = getTag(key) as? SpringAnimation
    return springAnimation != null && springAnimation.isRunning
}

/**
 * Set the key value to check if is animation is running using the view tag system.
 *
 * @param key The key of the tag, it must be a string resource id.
 * @param animationMode The value to be store at the key. Must be [AnimationMode.NONE_ANIMATION_MODE],
 * [AnimationMode.ENTER_ANIMATION_MODE] or [AnimationMode.POP_ANIMATION_MODE].
 */
internal fun View.setAnimationRunning(key: Int, animationMode: AnimationMode) {
    runCatching {
        setTag(key, animationMode)
    }
}

internal fun ViewPropertyAnimator.onAnimationEnd(onEnd: () -> Unit): ViewPropertyAnimator {
    return withEndAction {
        onEnd()
    }
}
