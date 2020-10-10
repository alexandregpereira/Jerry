package br.alexandregpereira.jerry

import android.graphics.Paint
import android.widget.TextView

fun TextView.setStrikeThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.setTextOrGone(text: String?) {
    if (text == null || text.trim().isEmpty()) {
        this.gone()
        return
    }

    this.visible()
    setText(text)
}
