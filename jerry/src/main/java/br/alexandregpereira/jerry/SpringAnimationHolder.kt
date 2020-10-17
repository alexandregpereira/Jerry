package br.alexandregpereira.jerry

import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation

internal data class SpringAnimationHolder(
    val springAnimation: SpringAnimation,
    val onAnimationEndListener: DynamicAnimation.OnAnimationEndListener? = null
)
