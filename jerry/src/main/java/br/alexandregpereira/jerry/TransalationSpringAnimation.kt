package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringForce

fun List<JerryAnimation>.translationXSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    stiffness = stiffness
)

fun View.translationXSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    stiffness = stiffness
)

fun List<JerryAnimation>.translationYSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    stiffness = stiffness
)

fun View.translationYSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    stiffness = stiffness
)
