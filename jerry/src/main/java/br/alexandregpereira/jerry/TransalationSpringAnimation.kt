package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringForce

fun JerryAnimation.translationXSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio,
)

fun JerryAnimationSet.translationXSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio,
)

fun View.translationXSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio,
)

fun JerryAnimation.translationYSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio,
)

fun JerryAnimationSet.translationYSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio,
)

fun View.translationYSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    targetValue = targetValue,
    stiffness = stiffness,
    dampingRatio = dampingRatio,
)
