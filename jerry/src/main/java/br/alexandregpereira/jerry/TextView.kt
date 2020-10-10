package br.alexandregpereira.jerry

import android.graphics.Paint
import android.widget.TextView

/**
 * Set the [Paint.STRIKE_THRU_TEXT_FLAG] flag to a [TextView].
 */
fun TextView.setStrikeThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

/**
 * Set the text of a [TextView] and change the [TextView] visibility depending of the [text] value.
 * If [text] is null or empty, the text is gone, else is visible.
 *
 * @see [visibleOrGone]
 */
fun TextView.setTextOrGone(text: String?) {
    visibleOrGone(visible = text != null && text.trim().isNotEmpty())
    setText(text)
}
