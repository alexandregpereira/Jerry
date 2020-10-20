package br.alexandregpereira.jerry.animation

import android.view.View
import androidx.annotation.RequiresApi
import androidx.dynamicanimation.animation.FloatPropertyCompat
import br.alexandregpereira.jerry.JerryAnimation
import br.alexandregpereira.jerry.JerryAnimationSet
import br.alexandregpereira.jerry.SpringAnimationPropertyKey
import br.alexandregpereira.jerry.spring

@RequiresApi(21)
fun JerryAnimationSet.elevationSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    targetValue = targetValue
)

@RequiresApi(21)
fun JerryAnimation.elevationSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    targetValue = targetValue
)

@RequiresApi(21)
fun View.elevationSpring(
    targetValue: Float
) = spring(
    key = SpringAnimationPropertyKey.ELEVATION.id,
    property = elevationViewProperty(),
    targetValue = targetValue
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
