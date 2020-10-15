package br.alexandregpereira.jerry

import androidx.annotation.StringRes

enum class OriginalValueKey(@StringRes val id: Int) {
    HEIGHT(R.string.expanding_collapsing_height_original_value_key),
    WIDTH(R.string.expanding_collapsing_width_original_value_key)
}
