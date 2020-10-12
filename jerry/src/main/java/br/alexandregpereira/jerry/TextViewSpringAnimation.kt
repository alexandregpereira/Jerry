package br.alexandregpereira.jerry

import android.widget.TextView
import androidx.dynamicanimation.animation.SpringAnimation

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
    onAnimationEnd: (() -> Unit)? = null
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
