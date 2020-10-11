package br.alexandregpereira.jerry

import android.view.View
import android.view.ViewPropertyAnimator
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

const val ANIMATION_SHORT_TIME = 200L
const val ANIMATION_MEDIUM_TIME = 400L
const val ANIMATION_LONG_TIME = 600L

const val ANIMATION_FAST_STIFFNESS = 600f

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
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    onValueUpdated: ((progress: Float) -> Unit)? = null
): SpringAnimation {
    var springAnimation = getTag(key) as? SpringAnimation
    if (springAnimation == null) {
        springAnimation = SpringAnimation(this, property).apply {
            spring = SpringForce().apply {
                this.dampingRatio = dampingRatio
                this.stiffness = stiffness
            }
            onValueUpdated?.let {
                addUpdateListener { _, value, _ ->
                    onValueUpdated(value)
                }
            }
        }
        setTag(key, springAnimation)
    }
    return springAnimation
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
