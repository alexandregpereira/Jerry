package br.alexandregpereira.jerry.animation

import android.view.View
import androidx.annotation.RequiresApi
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.alexandregpereira.jerry.JerryAnimation
import br.alexandregpereira.jerry.JerryAnimationSet
import br.alexandregpereira.jerry.SpringAnimationPropertyKey
import br.alexandregpereira.jerry.addOnScrolled
import br.alexandregpereira.jerry.spring
import br.alexandregpereira.jerry.start

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
fun RecyclerView.startLiftAnimation(liftView: View, elevation: Float) {
    val layoutManager = this.layoutManager as? LinearLayoutManager ?: return
    val adapter = this.adapter
    val targetValue: Float = if (
        adapter != null
        && adapter.itemCount > 0
        && layoutManager.findFirstCompletelyVisibleItemPosition() > 0
    ) {
        elevation
    } else {
        0f
    }

    if (liftView.elevation == targetValue) {
        return
    }

    val animation = liftView.elevationSpring(targetValue = targetValue)
    if (animation.targetValue == targetValue && animation.isRunning.not()) {
        animation.start()
    }
}

fun RecyclerView.startLiftAnimationCompat(liftView: View, elevation: Float) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        this.startLiftAnimation(liftView, elevation)
    }
}

@RequiresApi(21)
fun RecyclerView.setupLiftViewOnScroll(liftView: View, elevation: Float) {
    if (layoutManager !is LinearLayoutManager) return
    addOnScrolled {
        this.startLiftAnimation(liftView, elevation)
    }
}

fun RecyclerView.setupLiftViewOnScrollCompat(liftView: View, elevation: Float) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        this.setupLiftViewOnScroll(liftView, elevation)
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
