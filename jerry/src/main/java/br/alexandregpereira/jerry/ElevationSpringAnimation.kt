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
fun View.startElevationInSpringAnimation(
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = startElevationSpringAnimation(
    targetValue = getOrStoreElevationOriginalValue(),
    stiffness = stiffness,
    onAnimationEnd = onAnimationEnd
)

@RequiresApi(21)
fun View.startElevationOutSpringAnimation(
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) = startElevationSpringAnimation(
    targetValue = 0f,
    stiffness = stiffness,
    onAnimationEnd = onAnimationEnd
)

@RequiresApi(21)
fun View.startElevationSpringAnimation(
    targetValue: Float,
    stiffness: Float = SpringForce.STIFFNESS_LOW,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    getOrStoreElevationOriginalValue()
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
internal fun View.getOrStoreElevationOriginalValue(): Float {
    val key = OriginalValueKey.ELEVATION.id
    return runCatching {
        getTag(key) as Float
    }.getOrElse {
        elevation.apply {
            setTag(key, this)
        }
    }
}

fun View.clearElevationOriginalValue() {
    runCatching {
        setTag(OriginalValueKey.ELEVATION.id, null)
    }
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
