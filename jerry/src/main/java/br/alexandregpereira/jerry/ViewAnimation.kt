package br.alexandregpereira.jerry

import android.view.View
import android.view.ViewPropertyAnimator
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

const val ANIMATION_SHORT_TIME = 200L

const val ANIMATION_STIFFNESS = 500f

/**
 * Used to clear the key to verify if a animation is running.
 */
internal const val NONE_ANIMATION_MODE = 0

/**
 * Used to identify if the animation that is running is an enter animation.
 */
internal const val ENTER_ANIMATION_MODE = 1

/**
 * Used to identify if the animation that is running is an pop animation.
 */
internal const val POP_ANIMATION_MODE = 2

fun View.spring(
    key: Int,
    property: FloatPropertyCompat<View>,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    stiffness: Float = SpringForce.STIFFNESS_LOW
): SpringAnimation {
    var springAnimation = getTag(key) as? SpringAnimation
    if (springAnimation == null) {
        springAnimation = SpringAnimation(this, property).apply {
            spring = SpringForce().apply {
                this.dampingRatio = dampingRatio
                this.stiffness = stiffness
            }
        }
        setTag(key, springAnimation)
    }
    return springAnimation
}

fun View.startSpringAnimation(
    key: Int,
    property: FloatPropertyCompat<View>,
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    endListenerPair: Pair<Int, (() -> Unit)>? = null
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
    onAnimationEnd: () -> Unit
) {
    DynamicAnimation.OnAnimationEndListener { _, _, _, _ ->
        view.getSpringEndListener(key)?.let {
            this.removeEndListener(it)
        }
        onAnimationEnd()
    }.let {
        this.addEndListener(it)
        view.setTag(key, it)
    }
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
 * @param animationMode The value to be store at the key. Must be [NONE_ANIMATION_MODE],
 * [ENTER_ANIMATION_MODE] or [POP_ANIMATION_MODE].
 */
internal fun View.isAnimationRunning(key: Int, animationMode: Int): Boolean {
    return runCatching {
        getTag(key) as Int
    }.getOrElse {
        0
    } == animationMode
}

/**
 * Set the key value to check if is animation is running using the view tag system.
 *
 * @param key The key of the tag, it must be a string resource id.
 * @param animationMode The value to be store at the key. Must be [NONE_ANIMATION_MODE],
 * [ENTER_ANIMATION_MODE] or [POP_ANIMATION_MODE].
 */
internal fun View.setAnimationRunning(key: Int, animationMode: Int) {
    runCatching {
        setTag(key, animationMode)
    }
}

internal fun ViewPropertyAnimator.onAnimationEnd(onEnd: () -> Unit): ViewPropertyAnimator {
    return withEndAction {
        onEnd()
    }
}
