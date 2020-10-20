package br.alexandregpereira.jerry.animation

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import br.alexandregpereira.jerry.JerryAnimation
import br.alexandregpereira.jerry.JerryAnimationSet
import br.alexandregpereira.jerry.SpringAnimationPropertyKey
import br.alexandregpereira.jerry.spring

fun JerryAnimation.scaleXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.SCALE_X.id,
    property = DynamicAnimation.SCALE_X,
    targetValue = targetValue
)

fun JerryAnimationSet.scaleXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.SCALE_X.id,
    property = DynamicAnimation.SCALE_X,
    targetValue = targetValue
)

fun View.scaleXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.SCALE_X.id,
    property = DynamicAnimation.SCALE_X,
    targetValue = targetValue
)

fun JerryAnimation.scaleYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.SCALE_Y.id,
    property = DynamicAnimation.SCALE_Y,
    targetValue = targetValue
)

fun JerryAnimationSet.scaleYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.SCALE_Y.id,
    property = DynamicAnimation.SCALE_Y,
    targetValue = targetValue
)

fun View.scaleYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.SCALE_Y.id,
    property = DynamicAnimation.SCALE_Y,
    targetValue = targetValue
)
