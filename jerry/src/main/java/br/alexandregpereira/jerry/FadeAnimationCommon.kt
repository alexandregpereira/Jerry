package br.alexandregpereira.jerry

import android.view.View

fun View.isFadeOutRunning() = getSpringAnimation(SpringAnimationPropertyKey.ALPHA.id)?.run {
            isRunning && spring?.finalPosition == 0f
        } == true
