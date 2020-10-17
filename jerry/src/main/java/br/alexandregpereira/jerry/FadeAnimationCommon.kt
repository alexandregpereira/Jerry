package br.alexandregpereira.jerry

import android.view.View

internal fun View.isFadeInFadeOutRunning(animationMode: AnimationMode): Boolean =
    isAnimationRunning(R.string.is_fade_in_fade_out_key, animationMode)

internal fun View.setFadeInFadeOutRunning(animationMode: AnimationMode) =
    setAnimationRunning(R.string.is_fade_in_fade_out_key, animationMode)

fun View.isFadeInRunning() = isFadeInFadeOutRunning(AnimationMode.ENTER_ANIMATION_MODE)

fun View.isFadeOutRunning() = isFadeInFadeOutRunning(AnimationMode.POP_ANIMATION_MODE)

internal fun View.clearFadeInFadeOutRunning() = setFadeInFadeOutRunning(AnimationMode.NONE_ANIMATION_MODE)

internal fun View.startFadeInRunning() = setFadeInFadeOutRunning(AnimationMode.ENTER_ANIMATION_MODE)

internal fun View.startFadeOutRunning() = setFadeInFadeOutRunning(AnimationMode.POP_ANIMATION_MODE)
