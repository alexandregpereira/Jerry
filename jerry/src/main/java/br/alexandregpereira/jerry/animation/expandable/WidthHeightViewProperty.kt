package br.alexandregpereira.jerry.animation.expandable

import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.FloatPropertyCompat

internal fun View.widthHeightViewProperty(
    isHeight: Boolean = true,
    onProgressChange: ((progress: Float) -> Unit)? = null
) = object : FloatPropertyCompat<View>("viewProperty") {

    private val originalValue: Int
        get() = getOrStoreWidthOrHeightOriginalValue(isHeight)

    private val initialValue: Int
        get() = if (isExpandingRunning()) {
            (getLayoutParamSize(isHeight)).let {
                if (it == originalValue || it < 0) 0 else it
            }
        } else {
            getCollapsingInitialValue(isHeight)
        }

    private var targetValue: Int? = null

    private val progressFunction by lazy { createProgressFunction() }

    override fun getValue(view: View?): Float {
        targetValue =  if (isExpandingRunning()) {
            getTargetValue(originalValue, isHeight)
        } else {
            null
        }
        return initialValue.toFloat()
    }

    override fun setValue(view: View?, value: Float) {
        val targetValue = targetValue
        val finalValue = value.toInt().let {
            if (it == 0) {
                1
            } else {
                if (it == targetValue &&
                    originalValue == ViewGroup.LayoutParams.WRAP_CONTENT
                ) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    it
                }
            }
        }

        setLayoutParamSize(finalValue, isHeight)
        onProgressChange?.let {
            it(progressFunction(value))
        }
        requestLayout()
    }

    private fun createProgressFunction(): (Float) -> Float {
        val fullValue = targetValue ?: initialValue
        return if (isExpandingRunning()) {
            { value ->
                (fullValue - (fullValue - value)) / fullValue
            }
        } else {
            { value ->
                (fullValue - value) / fullValue
            }
        }
    }
}
