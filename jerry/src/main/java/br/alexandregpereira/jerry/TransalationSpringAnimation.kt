package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringForce

fun View.translationXSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_X.id,
    property = DynamicAnimation.TRANSLATION_X,
    stiffness = stiffness
)

fun View.startTranslationXSpringAnimation(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    startSpringAnimation(
        key = SpringAnimationPropertyKey.TRANSLATION_X.id,
        property = DynamicAnimation.TRANSLATION_X,
        targetValue = targetValue,
        stiffness = stiffness,
        endListenerPair = onAnimationEnd?.let {
            R.string.translation_x_end_listener_key to onAnimationEnd
        }
    )
}

fun View.translationYSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
    property = DynamicAnimation.TRANSLATION_Y,
    stiffness = stiffness
)

fun View.startTranslationYSpringAnimation(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    startSpringAnimation(
        key = SpringAnimationPropertyKey.TRANSLATION_Y.id,
        property = DynamicAnimation.TRANSLATION_Y,
        targetValue = targetValue,
        stiffness = stiffness,
        endListenerPair = onAnimationEnd?.let {
            R.string.translation_y_end_listener_key to onAnimationEnd
        }
    )
}
