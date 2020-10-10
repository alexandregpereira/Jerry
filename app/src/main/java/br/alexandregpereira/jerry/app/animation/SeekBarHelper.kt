package br.alexandregpereira.jerry.app.animation

import br.alexandregpereira.jerry.ANIMATION_SHORT_TIME

fun Int.getSeekBarAnimationDuration(): Long {
    return this.toLong() + ANIMATION_SHORT_TIME
}