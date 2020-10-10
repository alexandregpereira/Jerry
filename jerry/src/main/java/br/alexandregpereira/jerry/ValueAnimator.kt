package br.alexandregpereira.jerry

import android.animation.TypeEvaluator
import android.animation.ValueAnimator

/**
 * Goes from a origin value [fromValue] to the final value [toValue] using the [ValueAnimator].
 * The lambda function [onValueChange] is called when the animation value is updated.
 * When the value arrived at the [toValue], the [onAnimationEnd] is called to indicates that the
 * animation is finished.
 *
 * @param duration Indicates the time that the [ValueAnimator] will take to goes from the
 * [fromValue] to the [toValue].
 *
 * @see [ValueAnimator]
 */
fun startValueAnimator(
    fromValue: Float,
    toValue: Float,
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null,
    onValueChange: (Float) -> Unit
): ValueAnimator {

    return ValueAnimator.ofFloat(fromValue, toValue)
        .startValueAnimator(
            toValue,
            duration,
            onAnimationEnd = onAnimationEnd,
            onValueChange = onValueChange
        )
}

/**
 * Goes from a origin value [fromValue] to the final value [toValue] using the [ValueAnimator].
 * The lambda function [onValueChange] is called when the animation value is updated.
 * When the value arrived at the [toValue], the [onAnimationEnd] is called to indicates that the
 * animation is finished. This is the same as [startValueAnimator], except this function receives
 * a [TypeEvaluator] to allow developers to create animations on arbitrary property types,
 * by allowing them to supply custom evaluators for types that are not automatically understood and
 * used by the animation system.
 *
 * @param duration Indicates the time that the [ValueAnimator] will take to goes from the
 * [fromValue] to the [toValue].
 *
 * @see [ValueAnimator]
 * @see [TypeEvaluator]
 */
fun TypeEvaluator<*>.startValueAnimator(
    fromValue: Float,
    toValue: Float,
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null,
    onValueChange: (Float) -> Unit
): ValueAnimator {

    return ValueAnimator.ofObject(this, fromValue.toInt(), toValue.toInt())
        .startValueAnimator(
            toValue,
            duration,
            onAnimationEnd = onAnimationEnd,
            onValueChange = onValueChange
        )
}

private fun ValueAnimator.startValueAnimator(
    toValue: Float,
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null,
    onValueChange: (Float) -> Unit
): ValueAnimator {
    this.duration = duration

    this.addUpdateListener { valueAnimator ->
        val animatedValue = valueAnimator.animatedValue as Float
        onValueChange(animatedValue)
        if (animatedValue == toValue) onAnimationEnd?.invoke()
    }

    return this.apply { start() }
}