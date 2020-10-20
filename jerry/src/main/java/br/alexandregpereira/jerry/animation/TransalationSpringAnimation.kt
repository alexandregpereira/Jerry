package br.alexandregpereira.jerry.animation

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import br.alexandregpereira.jerry.JerryAnimation
import br.alexandregpereira.jerry.JerryAnimationSet
import br.alexandregpereira.jerry.SpringAnimationPropertyKey
import br.alexandregpereira.jerry.spring

fun JerryAnimation.translationXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    targetValue = targetValue
)

fun JerryAnimationSet.translationXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    targetValue = targetValue
)

fun View.translationXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    targetValue = targetValue
)

fun JerryAnimation.translationYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    targetValue = targetValue
)

fun JerryAnimationSet.translationYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    targetValue = targetValue
)

fun View.translationYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    targetValue = targetValue
)
