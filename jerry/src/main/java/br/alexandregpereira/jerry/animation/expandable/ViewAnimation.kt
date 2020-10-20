package br.alexandregpereira.jerry.animation.expandable

import android.view.View

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

/**
 * Check if is animation is running using the view tag system.
 *
 * @param key The key of the tag must be a string resource id.
 * @param animationMode The value to be store at the key. Must be [AnimationMode.NONE_ANIMATION_MODE],
 * [AnimationMode.ENTER_ANIMATION_MODE] or [AnimationMode.POP_ANIMATION_MODE].
 */
internal fun View.isAnimationRunning(key: Int, animationMode: AnimationMode): Boolean {
    return runCatching {
        getTag(key) as AnimationMode
    }.getOrElse {
        AnimationMode.NONE_ANIMATION_MODE
    } == animationMode
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
