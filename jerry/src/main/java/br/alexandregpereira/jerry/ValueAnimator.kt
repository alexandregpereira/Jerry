package br.alexandregpereira.jerry

import android.animation.TypeEvaluator
import android.animation.ValueAnimator

fun startValueAnimator(
    fromValue: Float,
    toValue: Float,
    duration: Long = ANIMATION_SHORT_TIME,
    onValueChange: (Float) -> Unit,
    onAnimationEnd: (() -> Unit)? = null
): ValueAnimator {

    return ValueAnimator.ofFloat(fromValue, toValue)
        .startValueAnimator(
            toValue,
            duration,
            onValueChange = onValueChange,
            onAnimationEnd = onAnimationEnd
        )
}

fun TypeEvaluator<*>.startValueAnimator(
    fromValue: Float,
    toValue: Float,
    duration: Long = ANIMATION_SHORT_TIME,
    onValueChange: (Float) -> Unit,
    onAnimationEnd: (() -> Unit)? = null
): ValueAnimator {

    return ValueAnimator.ofObject(this, fromValue.toInt(), toValue.toInt())
        .startValueAnimator(
            toValue,
            duration,
            onValueChange = onValueChange,
            onAnimationEnd = onAnimationEnd
        )
}

fun ValueAnimator.startValueAnimator(
    toValue: Float,
    duration: Long = ANIMATION_SHORT_TIME,
    onValueChange: (Float) -> Unit,
    onAnimationEnd: (() -> Unit)? = null
): ValueAnimator {
    this.duration = duration

    this.addUpdateListener { valueAnimator ->
        val animatedValue = valueAnimator.animatedValue as Float
        onValueChange(animatedValue)
        if (animatedValue == toValue) onAnimationEnd?.invoke()
    }

    return this.apply { start() }
}