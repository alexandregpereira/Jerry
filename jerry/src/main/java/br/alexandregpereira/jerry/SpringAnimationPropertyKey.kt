package br.alexandregpereira.jerry

import androidx.annotation.StringRes

enum class SpringAnimationPropertyKey(@StringRes val id: Int) {
    ALPHA(R.string.alpha_spring_key),
    ELEVATION(R.string.elevation_spring_key),
    TRANSLATION_X(R.string.translation_x_spring_key),
    TRANSLATION_Y(R.string.translation_y_spring_key),
    ROTATION(R.string.rotation_spring_key),
    ROTATION_X(R.string.rotation_x_spring_key),
    ROTATION_Y(R.string.rotation_y_spring_key),
    HEIGHT(R.string.expanding_collapsing_height_spring_key),
    WIDTH(R.string.expanding_collapsing_width_spring_key)
}
