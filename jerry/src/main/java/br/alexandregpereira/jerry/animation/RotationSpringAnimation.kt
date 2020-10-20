package br.alexandregpereira.jerry.animation

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import br.alexandregpereira.jerry.JerryAnimation
import br.alexandregpereira.jerry.JerryAnimationSet
import br.alexandregpereira.jerry.SpringAnimationPropertyKey
import br.alexandregpereira.jerry.spring

fun JerryAnimation.rotationSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION.id,
    property = DynamicAnimation.ROTATION,
    targetValue = targetValue
)

fun JerryAnimationSet.rotationSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION.id,
    property = DynamicAnimation.ROTATION,
    targetValue = targetValue
)

fun View.rotationSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION.id,
    property = DynamicAnimation.ROTATION,
    targetValue = targetValue
)

fun JerryAnimation.rotationXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION_X.id,
    property = DynamicAnimation.ROTATION_X,
    targetValue = targetValue
)

fun JerryAnimationSet.rotationXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION_X.id,
    property = DynamicAnimation.ROTATION_X,
    targetValue = targetValue
)

fun View.rotationXSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION_X.id,
    property = DynamicAnimation.ROTATION_X,
    targetValue = targetValue
)

fun JerryAnimation.rotationYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION_Y.id,
    property = DynamicAnimation.ROTATION_Y,
    targetValue = targetValue
)

fun JerryAnimationSet.rotationYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION_Y.id,
    property = DynamicAnimation.ROTATION_Y,
    targetValue = targetValue
)

fun View.rotationYSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ROTATION_Y.id,
    property = DynamicAnimation.ROTATION_Y,
    targetValue = targetValue
)
