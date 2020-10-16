package br.alexandregpereira.jerry

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

const val ANIMATION_STIFFNESS = 600f

fun View.cancelSpringAnimation() {
    getSpringAnimationKeys().forEach {
        cancelSpringAnimation(key = it)
    }
}

fun View.cancelSpringAnimation(key: Int) {
    getSpringAnimation(key)?.cancel()
}

fun View.skipToEndSpringAnimation() {
    getSpringAnimationKeys().forEach {
        skipToEndSpringAnimation(key = it)
    }
}

fun View.skipToEndSpringAnimation(key: Int) {
    getSpringAnimation(key)?.takeIf { it.canSkipToEnd() }?.skipToEnd()
}

fun View.clearSpring(key: Int) {
    setTag(key, null)
}

/**
 *
 */
fun View.spring(
    key: Int,
    property: FloatPropertyCompat<View>,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    stiffness: Float = SpringForce.STIFFNESS_LOW
): JerryAnimation {
    var springAnimation = getSpringAnimation(key)
    if (springAnimation == null) {
        springAnimation = SpringAnimation(this, property).apply {
            spring = SpringForce().apply {
                this.dampingRatio = dampingRatio
                this.stiffness = stiffness
            }
        }
        addSpringAnimationKeyIfNotContains(key)
        setTag(key, SpringAnimationHolder(springAnimation))
    }
    return JerryAnimation(key = key, view = this, springAnimation)
}

fun JerryAnimation.spring(
    key: Int,
    property: FloatPropertyCompat<View>,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    stiffness: Float = SpringForce.STIFFNESS_LOW
): JerryAnimationSet {
    return animationSet().spring(key, property, dampingRatio, stiffness)
}

fun JerryAnimationSet.spring(
    key: Int,
    property: FloatPropertyCompat<View>,
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    stiffness: Float = SpringForce.STIFFNESS_LOW
): JerryAnimationSet {
    return this.copy(
        jerryAnimations = jerryAnimations +
                listOf(jerryAnimations.last().view.spring(key, property, dampingRatio, stiffness))
    )
}

fun JerryAnimation.startSpringAnimation(
    targetValue: Float,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {

    this.addSpringEndListener(
        onAnimationEnd = onAnimationEnd
    )

    springAnimation.animateToFinalPosition(targetValue)
}

private fun JerryAnimation.animationSet(): JerryAnimationSet {
    return JerryAnimationSet(jerryAnimations = listOf(this))
}

fun JerryAnimationSet.target(targetValue: Float): JerryAnimationSet {
    return this.copy(
        jerryAnimations = jerryAnimations.subList(0, jerryAnimations.size - 1) +
                jerryAnimations.last().target(targetValue)
    )
}

fun JerryAnimation.target(targetValue: Float): JerryAnimation {
    return this.copy(targetValue = targetValue)
}

fun JerryAnimation.after(jerryAnimationSet: JerryAnimationSet): JerryAnimationSet {
    return animationSet().after(jerryAnimationSet)
}

fun JerryAnimation.after(jerryAnimation: JerryAnimation): JerryAnimationSet {
    return animationSet().after(jerryAnimation)
}

fun JerryAnimationSet.after(jerryAnimationSet: JerryAnimationSet): JerryAnimationSet {
    return this.copy(jerryAfterAnimationSet = jerryAnimationSet)
}

fun JerryAnimationSet.after(jerryAnimation: JerryAnimation): JerryAnimationSet {
    return this.copy(jerryAfterAnimationSet = jerryAnimation.animationSet())
}

fun JerryAnimationSet.startSpringAnimation(
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    val onAnimationsEnd: (canceled: Boolean, completed: Boolean) -> Unit = { canceled, completed ->
        if (completed) {
            if (canceled || jerryAfterAnimationSet == null) {
                onAnimationEnd?.invoke(canceled)
            } else {
                jerryAfterAnimationSet.startSpringAnimation(onAnimationEnd)
            }
        }
    }
    return jerryAnimations.forEachIndexed { index, animation ->
        val targetValue = animation.targetValue ?: throw IllegalStateException(
            "The animation target from index $index must be set before execution of a list of animations"
        )
        val otherAnimations = jerryAnimations.filterNot { it.key == animation.key }
        animation.startSpringAnimation(targetValue) { canceled ->
            onAnimationsEnd(
                canceled,
                otherAnimations.isRunning().not()
            )
        }
    }
}

fun List<JerryAnimation>.isRunning(): Boolean {
    return isNotEmpty() && map { it.isRunning }.reduce { acc, isRunning -> acc || isRunning }
}

internal fun JerryAnimation.addSpringEndListener(
    onAnimationEnd: ((canceled: Boolean) -> Unit)?
) {
    view.getSpringEndListener(key = key)?.also { endListener ->
        springAnimation.removeEndListener(endListener)
    }

    if (onAnimationEnd == null) return

    DynamicAnimation.OnAnimationEndListener { animation, canceled, _, _ ->
        view.getSpringEndListener(key)?.let {
            animation.removeEndListener(it)
        }
        onAnimationEnd(canceled)
    }.let { endListener ->
        view.getSpringAnimationHolder(key)?.let { holder ->
            this@addSpringEndListener.springAnimation.addEndListener(endListener)
            view.setTag(key, holder.copy(onAnimationEndListener = endListener))
        } ?: throw IllegalStateException("You should call spring method before")
    }
}

fun View.getSpringAnimation(key: Int): SpringAnimation? {
    return getSpringAnimationHolder(key)?.springAnimation
}

private fun View.getSpringEndListener(key: Int): DynamicAnimation.OnAnimationEndListener? {
    return getSpringAnimationHolder(key)?.onAnimationEndListener
}

private fun View.getSpringAnimationHolder(key: Int): SpringAnimationHolder? {
    return getTag(key).run {
        this as? SpringAnimationHolder
    }
}

private fun View.getSpringAnimationKeys(): List<Int> {
    return getTag(R.string.keys_key).run {
        this as? List<*>
    }?.mapNotNull {
        it as? Int
    } ?: listOf<Int>().apply {
        setTag(R.string.keys_key, this)
    }
}

private fun View.addSpringAnimationKeyIfNotContains(key: Int) {
    val animationKeys = getSpringAnimationKeys()
    if (animationKeys.contains(key).not()) {
        setTag(R.string.keys_key, animationKeys.toMutableList().apply {
            add(key)
        })
    }
}

