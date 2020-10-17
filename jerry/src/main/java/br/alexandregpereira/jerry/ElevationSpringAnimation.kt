package br.alexandregpereira.jerry

import android.view.View
import androidx.annotation.RequiresApi
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringForce

@RequiresApi(21)
fun JerryAnimationSet.elevationSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    targetValue = targetValue,
    dampingRatio = dampingRatio,
    stiffness = stiffness
)

@RequiresApi(21)
fun JerryAnimation.elevationSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    targetValue = targetValue,
    dampingRatio = dampingRatio,
    stiffness = stiffness
)

@RequiresApi(21)
fun View.elevationSpring(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    targetValue = targetValue,
    dampingRatio = dampingRatio,
    stiffness = stiffness
)

@RequiresApi(21)
fun elevationViewProperty() = object : FloatPropertyCompat<View>(
    "viewProperty"
) {
    override fun setValue(view: View?, value: Float) {
        view?.elevation = value
    }

    override fun getValue(view: View?): Float {
        return view?.elevation ?: 0f
    }
}
