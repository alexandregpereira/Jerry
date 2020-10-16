package br.alexandregpereira.jerry

import android.view.View
import androidx.annotation.RequiresApi
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringForce

@RequiresApi(21)
fun JerryAnimationSet.elevationSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    stiffness = stiffness
)

@RequiresApi(21)
fun JerryAnimation.elevationSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    stiffness = stiffness
)

@RequiresApi(21)
fun View.elevationSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
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
