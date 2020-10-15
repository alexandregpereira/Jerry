package br.alexandregpereira.jerry

import android.view.View
import androidx.annotation.RequiresApi
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringForce

@RequiresApi(21)
fun View.elevationSpring(
    stiffness: Float = SpringForce.STIFFNESS_LOW
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    stiffness = stiffness
)

@RequiresApi(21)
fun View.startElevationSpringAnimation(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    startSpringAnimation(
        key = SpringAnimationPropertyKey.ELEVATION.id,
        property = elevationViewProperty(),
        targetValue = targetValue,
        stiffness = stiffness,
        endListenerPair = onAnimationEnd?.let {
            R.string.elevation_end_listener_key to it
        }
    )
}

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
