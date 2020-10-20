package br.alexandregpereira.jerry.textview

import android.widget.TextView
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import br.alexandregpereira.jerry.ANIMATION_STIFFNESS
import br.alexandregpereira.jerry.expandable.goneCollapseHeightFadeOut
import br.alexandregpereira.jerry.expandable.isCollapsingRunning
import br.alexandregpereira.jerry.expandable.visibleExpandHeightFadeIn
import br.alexandregpereira.jerry.fadeInSpring
import br.alexandregpereira.jerry.fadeOutSpring
import br.alexandregpereira.jerry.isFadeOutRunning
import br.alexandregpereira.jerry.isVisible
import br.alexandregpereira.jerry.start

/**
 * Uses the [setTextFadeSpring], [visibleExpandHeightFadeIn] or [goneCollapseHeightFadeOut]
 * animation methods depending of the TextView state. If the new text is null or empty,
 * the [goneCollapseHeightFadeOut] is used, else if the TextView is already visible,
 * the [setTextFadeSpring] is used, else the [visibleExpandHeightFadeIn] is used.
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
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    if (text == null || text.trim().isEmpty()) {
        goneCollapseHeightFadeOut(stiffness, dampingRatio, onAnimationEnd)
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
    visibleExpandHeightFadeIn(stiffness, dampingRatio, onAnimationEnd)
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
    dampingRatio: Float = SpringForce.DAMPING_RATIO_NO_BOUNCY,
    onAnimationEnd: ((canceled: Boolean) -> Unit)? = null
) {
    val textView = this
    val oldText = textView.text.toString()

    if (oldText == text) return

    if (oldText.isEmpty()) {
        textView.text = text
        textView.alpha = 0f
        fadeInSpring(stiffness = stiffness, dampingRatio).start(
            onAnimationEnd = onAnimationEnd
        )
        return
    }

    fadeOutSpring(stiffness = stiffness * 1.5f, dampingRatio)
        .start {
            textView.text = text
            fadeInSpring(stiffness = stiffness * 1.5f, dampingRatio).start(
                onAnimationEnd = onAnimationEnd
            )
        }
}
