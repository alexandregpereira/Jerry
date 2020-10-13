package br.alexandregpereira.jerry

import android.widget.TextView
import androidx.dynamicanimation.animation.SpringAnimation

/**
 * Uses the [setTextFadeSpring], [expandHeightFadingSpring] or [collapseHeightFadingSpring]
 * animation methods depending of the TextView state. If the new text is null or empty,
 * the [collapseHeightFadingSpring] is used, else if the TextView is already visible,
 * the [setTextFadeSpring] is used, else the [expandHeightFadingSpring] is used.
 *
 * @param text The new text of the TextView
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 *
 * @see [SpringAnimation]
 */
fun TextView.setTextExpandableSpring(
    text: String?,
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (text == null || text.trim().isEmpty()) {
        collapseHeightFadingSpring(stiffness = stiffness, onAnimationEnd = onAnimationEnd)
        return
    }

    if (isVisible() && isCollapsingRunning().not() && isFadeOutRunning().not()) {
        setTextFadeSpring(
            text,
            stiffness = stiffness,
            onAnimationEnd = onAnimationEnd
        )
        return
    }
    setText(text)
    expandHeightFadingSpring(stiffness = stiffness, onAnimationEnd = onAnimationEnd)
}

/**
 * Changes the text of the [TextView] using cross fade animation.
 *
 * @param text The new text of the TextView
 * @param stiffness Stiffness of a spring. The more stiff a spring is, the more force it applies to
 * the object attached when the spring is not at the final position. Default stiffness is
 * [ANIMATION_STIFFNESS].
 *
 * @see [SpringAnimation]
 */
fun TextView.setTextFadeSpring(
    text: String = "",
    stiffness: Float = ANIMATION_STIFFNESS,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    val textView = this
    val oldText = textView.text.toString()

    if (oldText == text) return

    if (oldText.isEmpty()) {
        textView.text = text
        textView.alpha = 0f
        startFadeSpringAnimation(
            targetValue = 1f,
            stiffness = stiffness,
            onAnimationEnd = onAnimationEnd
        )
        return
    }

    startFadeSpringAnimation(
        targetValue = 0f,
        stiffness = stiffness * 1.5f
    ) {
        textView.text = text
        startFadeSpringAnimation(
            targetValue = 1f,
            stiffness = stiffness * 1.5f,
            onAnimationEnd = onAnimationEnd
        )
    }
}
