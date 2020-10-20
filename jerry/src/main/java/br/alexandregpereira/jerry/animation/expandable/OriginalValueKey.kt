package br.alexandregpereira.jerry.animation.expandable

import androidx.annotation.StringRes
import br.alexandregpereira.jerry.R

internal enum class OriginalValueKey(@StringRes val id: Int) {
    HEIGHT(R.string.expanding_collapsing_height_original_value_key),
    WIDTH(R.string.expanding_collapsing_width_original_value_key)
}
