package br.alexandregpereira.jerry

import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView

/**
 * Uses the [setTextFade], [expandHeightFading] or [collapseHeightFading] animation methods
 * depending of the TextView state. If the new text is null or empty, the [collapseHeightFading]
 * is used, else if the TextView is already visible, the [setTextFade] is used, else the
 * [expandHeightFading] is used.
 *
 * @param text The new text of the TextView
 * @param duration The duration of the animation
 * @param onFirstFadeEnd The function to call when the first fade of the [setTextFade] is finished
 * @param onAnimationEnd The function to call when the animation is finished
 */
fun TextView.setTextExpandableAnimation(
    text: String?,
    duration: Long = ANIMATION_SHORT_TIME,
    onAnimationEnd: (() -> Unit)? = null,
    onFirstFadeEnd: (() -> Unit)? = null
) {
    if (text == null || text.trim().isEmpty()) {
        collapseHeightFading(onAnimationEnd = onAnimationEnd)
        return
    }

    if (isVisible() && isCollapsingRunning().not() && isFadeOutRunning().not()) {
        setTextFade(
            text,
            duration = duration,
            onFirstAnimationEnd = onFirstFadeEnd
        )
        return
    }
    setText(text)
    expandHeightFading(onAnimationEnd = onAnimationEnd)
}

/**
 * Changes the text of the [TextView] using cross fade animation.
 *
 * @param text The new text of the TextView
 * @param duration The duration of the animation
 * @param onFirstAnimationEnd The function to call when the first fade is finished
 */
fun TextView.setTextFade(
    text: String = "",
    duration: Long = ANIMATION_SHORT_TIME,
    onFirstAnimationEnd: (() -> Unit)? = null
) {
    val textView = this
    val oldText = textView.text.toString()

    if (oldText == text) return

    if (oldText.isEmpty()) {
        textView.text = text
        textView.alpha = 0f
        fadeInAnimation(
            duration = duration,
            interpolator = DecelerateInterpolator()
        )
        return
    }

    fadeOutAnimation(duration = duration, interpolator = AccelerateInterpolator())
        .onAnimationEnd {
            textView.text = text
            onFirstAnimationEnd?.invoke()
            fadeInAnimation(
                duration = duration,
                interpolator = DecelerateInterpolator()
            )
        }
}
