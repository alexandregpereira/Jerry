package br.alexandregpereira.jerry

import android.view.View

internal fun View.isFadeInFadeOutRunning(animationMode: Int): Boolean =
    isAnimationRunning(R.string.is_fade_in_fade_out_key, animationMode)

internal fun View.setFadeInFadeOutRunning(animationMode: Int) =
    setAnimationRunning(R.string.is_fade_in_fade_out_key, animationMode)

fun View.isFadeInRunning() = isFadeInFadeOutRunning(ENTER_ANIMATION_MODE)

fun View.isFadeOutRunning() = isFadeInFadeOutRunning(POP_ANIMATION_MODE)

internal fun View.clearFadeInFadeOutRunning() = setFadeInFadeOutRunning(NONE_ANIMATION_MODE)

internal fun View.startFadeInRunning() = setFadeInFadeOutRunning(ENTER_ANIMATION_MODE)

internal fun View.startFadeOutRunning() = setFadeInFadeOutRunning(POP_ANIMATION_MODE)
